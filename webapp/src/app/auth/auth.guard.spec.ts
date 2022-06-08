import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import {
  ActivatedRouteSnapshot,
  NavigationExtras,
  Router,
  RouterStateSnapshot,
} from '@angular/router';
import { Observable, of } from 'rxjs';

import { MizudoService } from '../services/mizudo.service';
import { SessionService } from '../services/session.service';
import { AuthGuard } from './auth.guard';

function fakeRouterState(url: string): RouterStateSnapshot {
  return {
    url,
  } as RouterStateSnapshot;
}

class MockSessionService extends SessionService {
  override isLogged(): Observable<boolean> {
    return of(false);
  }

  override autologin(): Observable<boolean> {
    return of(false);
  }
}

describe('AuthGuard', () => {
  let guard: AuthGuard;
  let sessionService: SessionService;
  let router: Router;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [
        {
          provide: SessionService,
          useClass: MockSessionService,
        },
        MizudoService,
        {
          provide: Storage,
          useValue: localStorage,
        },
        {
          provide: Router,
          useClass: class {
            navigate = jasmine.createSpy('navigate');
          },
        },
      ],
    });
    guard = TestBed.inject(AuthGuard);
    router = TestBed.inject(Router);
    sessionService = TestBed.inject(SessionService);
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });

  it('given an unauthenticated session and auto-login is disabled then canActivate() should returns Objectable<false>', (done: DoneFn) => {
    spyOn(sessionService, 'isLogged').and.returnValue(of(false));
    spyOn(sessionService, 'autologin').and.returnValue(of(false));
    (
      guard.canActivate(
        new ActivatedRouteSnapshot(),
        fakeRouterState('')
      ) as Observable<boolean>
    ).subscribe((value) => {
      expect(value).toBeFalsy();
      done();
    });
    expect(router.navigate).toHaveBeenCalledWith(['/login']);
    expect(sessionService.isLogged).toHaveBeenCalledTimes(1);
    expect(sessionService.autologin).toHaveBeenCalledTimes(1);
  });

  it('given an authenticated session and auto-login is disabled then canActivate() should returns Objectable<true>', (done: DoneFn) => {
    spyOn(sessionService, 'isLogged').and.returnValue(of(true));
    spyOn(sessionService, 'autologin').and.returnValue(of(false));
    (
      guard.canActivate(
        new ActivatedRouteSnapshot(),
        fakeRouterState('')
      ) as Observable<boolean>
    ).subscribe((value) => {
      expect(value).toBeTruthy();
      done();
    });
    expect(router.navigate).toHaveBeenCalledTimes(0);
    expect(sessionService.isLogged).toHaveBeenCalledTimes(1);
    expect(sessionService.autologin).toHaveBeenCalledTimes(0);
  });

  it('given an unauthenticated session but auto-login is enable then canActivate() should returns Objectable<true>', (done: DoneFn) => {
    spyOn(sessionService, 'isLogged').and.returnValue(of(false));
    spyOn(sessionService, 'autologin').and.returnValue(of(true));
    (
      guard.canActivate(
        new ActivatedRouteSnapshot(),
        fakeRouterState('')
      ) as Observable<boolean>
    ).subscribe((value) => {
      expect(value).toBeTruthy();
      done();
    });
    expect(router.navigate).toHaveBeenCalledTimes(0);
    expect(sessionService.isLogged).toHaveBeenCalledTimes(1);
    expect(sessionService.autologin).toHaveBeenCalledTimes(1);
  });
});
