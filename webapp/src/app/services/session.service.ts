import { Credentials } from './../model/Credentials';
import { MizudoService } from './mizudo.service';
import { Token } from './../model/Token';
import { Injectable } from '@angular/core';
import { BehaviorSubject, map, Observable, of, switchMap, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SessionService {

  private _token = new BehaviorSubject<Token | null>(null);

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
    return this.mizudoService
      .getToken({ username, password })
      .pipe(tap((token) => {
        this._token.next(token);
      }));
  }

  async logOut() {
    this._token.next(null);
  }
}
