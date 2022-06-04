import { Token } from 'src/app/model/Token';
import { Injectable } from '@angular/core';
import { BehaviorSubject, map, Observable, of, switchMap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class SessionService {

  private _token = new BehaviorSubject<Token | null>(null);

  constructor() {}

  getToken(): Observable<Token | null> {
    return this._token.asObservable();
  }

  setToken(token: Token) {
    this._token.next(token);
  }
}
