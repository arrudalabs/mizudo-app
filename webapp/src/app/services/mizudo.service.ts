import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient, HttpParams } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Credentials } from '../model/Credentials';
import { Token } from './../model/Token';
import { Profile } from '../model/Profile';

@Injectable({
  providedIn: 'root',
})
export class MizudoService {
  constructor(private http: HttpClient) {}

  getToken({ username, password }: Credentials): Observable<Token> {
    return this.http.post<Token>(
      `${environment.api}/resources/token`,
      new HttpParams().appendAll({ username, password }),
      {
        headers: {
          Accept: 'application/json',
          'Content-type': 'application/x-www-form-urlencoded',
        },
      }
    );
  }

  getProfile({access_token}: Token): Observable<Profile> {
    return this.http.get<Profile>(`${environment.api}/resources/profiles/me`, {
      headers: {
        Authorization: `Bearer ${access_token}`,
        Accept: 'application/json',
      },
    });
  }
}
