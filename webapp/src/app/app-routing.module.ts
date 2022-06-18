import { NgModule } from '@angular/core';
import { Route, RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './auth/auth.guard';
import { LoginComponent } from './components/login/login.component';
import { ProfileComponent } from './components/profile/profile.component';
import { MembersComponent } from './components/members/members.component';

export interface MRoute extends Route {
  isMenuItem?: boolean;
  icon?:string;
  label?: string;
}

export const routes: MRoute[] = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: '/profile',
  },
  {
    path: 'login',
    component: LoginComponent,
  },
  {
    path: 'profile',
    component: ProfileComponent,
    canActivate: [AuthGuard],
    label: 'My profile',
    isMenuItem: true,
    icon: 'person'
  },
  {
    path: 'members',
    component: MembersComponent,
    canActivate: [AuthGuard],
    isMenuItem: true,
    icon: 'people-outline',
    label: 'Members',
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
