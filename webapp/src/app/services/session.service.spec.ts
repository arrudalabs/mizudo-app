import { Token } from './../model/Token';
import { newFakeToken } from './../model/FakeToken';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { firstValueFrom, of } from 'rxjs';
import { MizudoService } from './mizudo.service';

import { SessionService } from './session.service';

class MockMizudoService extends MizudoService {}

describe('SessionService', () => {
  let service: SessionService;
  let mizudoService: MizudoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        { provide: MizudoService, useClass: MockMizudoService },
        { provide: Storage, useValue: localStorage },
      ],
    });
    mizudoService = TestBed.inject(MizudoService);
    service = TestBed.inject(SessionService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('given an unauthenticated session then getToken() should return an Observable<null>', (done: DoneFn) => {
    service.getToken().subscribe((value) => {
      expect(value).toBeNull();
      done();
    });
  });

  it('given an unauthenticated session then isLogged() should return an Observable<false>', (done: DoneFn) => {
    service.isLogged().subscribe((value) => {
      expect(value).toBeFalsy();
      done();
    });
  });

  it('given an unauthenticated session and no data in the storage then autologin() should return an Observable<false>', (done: DoneFn) => {
    localStorage.clear();
    service.autologin().subscribe((value) => {
      expect(value).toBeFalsy();
      done();
    });
  });

  async function authenticateSession() {
    const expectedToken = newFakeToken(10);
    spyOn(mizudoService, 'getToken').and.returnValue(of(expectedToken));

    const username = 'admin';
    const password = 'admin';

    const actualToken = await firstValueFrom(
      service.auth({
        username,
        password,
      })
    );
    return actualToken;
  }

  it('given an authenticated session then getToken() should return a valid Token', async () => {
    const expectedToken = await authenticateSession();

    const actualGetToken = await firstValueFrom(service.getToken());

    expect(actualGetToken).toEqual(expectedToken);
  });

  it('given an authenticated session then isLogged() should return an Observable<true>', async () => {
    const expectedToken = await authenticateSession();
    expect(await firstValueFrom(service.isLogged())).toBeTruthy();
  });

});
