import {
  HttpClientTestingModule,
  HttpTestingController,
} from '@angular/common/http/testing';
import { TestBed, getTestBed } from '@angular/core/testing';
import { Token } from './../model/Token';
import { environment } from './../../environments/environment';

import { MizudoService } from './mizudo.service';
import { HttpParams } from '@angular/common/http';
import { Profile } from '../model/Profile';
import { newFakeToken } from '../model/FakeToken';

describe('MizudoService', () => {
  let service: MizudoService;
  let httpMock: HttpTestingController;

  beforeEach(async () => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [MizudoService],
    });
    const injector = getTestBed();
    service = injector.inject(MizudoService);
    httpMock = injector.inject(HttpTestingController);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('#getToken', () => {
    const username = 'admin';
    const password = 'shoto';

    const expectedParams = new HttpParams().appendAll({ username, password });

    service.getToken({ username, password }).subscribe({
      next: (token: Token) => {
        expect(token).toBeTruthy();
        expect(token.access_token).toBeTruthy();
        expect(token.expires_in).toBeTruthy();
      },
      error: (err: any) => {
        fail(err);
      },
    });

    const req = httpMock.expectOne(`${environment.api}/resources/token`);
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual(expectedParams);
    expect(req.request.headers.get('Content-type')).toBe(
      'application/x-www-form-urlencoded'
    );
    expect(req.request.headers.get('Accept')).toBe('application/json');

    const expectedToken: Token = newFakeToken(60) ;

    req.flush(expectedToken);

  });



  it('#getProfile', () => {

    const token: Token = {
      access_token: '23kj4n23kj4n32',
      expires_in: 300,
    };

    const expectedProfile: Profile = {
      username:'john'
    };

    service.getProfile(token).subscribe({
      next:(value:Profile)=>{
        expect(value).toEqual(expectedProfile);
      },
      error:(error:any)=>{
        fail(error);
      }
    });

    const req = httpMock.expectOne(`${environment.api}/resources/profiles/me`);
    expect(req.request.method).toBe('GET');
    expect(req.request.headers.get('Authorization')).toBe(
      `Bearer ${token.access_token}`
    );
    expect(req.request.headers.get('Accept')).toBe('application/json');

    req.flush(expectedProfile);

  });


  afterEach(() => {
    httpMock.verify();
  });
});
