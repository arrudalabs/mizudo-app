import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router';
import { Observable, take, tap, switchMap, of } from 'rxjs';
import { SessionService } from '../services/session.service';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private session: SessionService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | Observable<boolean | UrlTree>
    | Promise<boolean | UrlTree>
    | boolean
    | UrlTree {
    return this.session.isLogged().pipe(
      take(1),
      switchMap((isLogged) => {
        return !isLogged ? this.session.autologin() : of(isLogged);
      }),
      tap((isLogged) => {
        if (!isLogged) {
          this.router.navigate(['/', 'login']);
        }
      })
    );
  }
}
