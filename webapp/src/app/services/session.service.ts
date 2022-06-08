import { Credentials } from './../model/Credentials';
import { MizudoService } from './mizudo.service';
import { Token } from './../model/Token';
import { Injectable } from '@angular/core';
import {
  BehaviorSubject,
  from,
  ignoreElements,
  map,
  Observable,
  of,
  switchMap,
  tap,
} from 'rxjs';
import { decodeJwt } from 'jose';

@Injectable({
  providedIn: 'root',
})
export class SessionService {

  static TOKEN_KEY = 'mizudoToken';

  private _token = new BehaviorSubject<Token | null>(null);

  private activeAutoLogoutTimer: any;

  constructor(private mizudoService: MizudoService) {}

  getToken(): Observable<Token | null> {
    return this._token.asObservable();
  }

  isLogged(): Observable<boolean> {
    return this.getToken().pipe(
      switchMap((token) => {
        return token ? of(true) : of(false);
      })
    );
  }

  auth({ username, password }: Credentials): Observable<Token> {
    return this.mizudoService.getToken({ username, password }).pipe(
      tap((token) => {
        this.storeToken(token);
      })
    );
  }

  storeToken(token: Token) {
    localStorage.setItem(SessionService.TOKEN_KEY, JSON.stringify(token));
    const durationInSeconds = token.expires_in;
    this.defineTokenAndAutoLogout(durationInSeconds, token);
  }

  private defineTokenAndAutoLogout(durationInSeconds: number, token: Token) {
    this.autologout(durationInSeconds);
    this._token.next(token);
  }

  autologout(durationIsSeconds: number) {
    this.destroyAutoLogoutTimeout();
    console.log(`auto-logout in ${durationIsSeconds} second(s)`);
    this.activeAutoLogoutTimer = setTimeout(async () => {
      console.log('calling auto-logout...');
      await this.logOut();
    }, durationIsSeconds * 1000);
  }

  private destroyAutoLogoutTimeout() {
    if (this.activeAutoLogoutTimer) {
      console.log(`destroying auto-logout...`);
      clearTimeout(this.activeAutoLogoutTimer);
    }
  }

  autologin(): Observable<boolean> {
    const storedData = localStorage.getItem(SessionService.TOKEN_KEY);

    if (!storedData) {
      return of(false);
    }
    return of(JSON.parse(storedData) as Token).pipe(
      map((storedToken) => {
        const payload = decodeJwt(storedToken.access_token);

        if (!payload.iat || !payload.exp) {
          return false;
        }

        const expirationTime = new Date(payload.exp * 1000);

        if (expirationTime <= new Date()) {
          return false;
        }

        this.defineTokenAndAutoLogout((payload.exp - payload.iat) ,storedToken);

        return true;
      })
    );
  }

  async logOut() {
    localStorage.removeItem(SessionService.TOKEN_KEY);
    this.destroyAutoLogoutTimeout();
    this._token.next(null);
  }
}
