import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { SignupComponent } from './pages/signup/signup.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { UserGuard } from './services/user.guard';
import { ProfileComponent } from './pages/profile/profile.component';
import { UserDashboardComponent } from './pages/user/user-dashboard/user-dashboard.component';
import { CreatePostComponent } from './components/create-post/create-post.component';

const routes: Routes = [
  {
  path : '',
  component : HomeComponent,
  pathMatch : 'full'
  },
  {
    path : 'signup',
    component : SignupComponent,
    pathMatch : 'full'
  },
  {
    path : 'login',
    component : LoginComponent,
    pathMatch : 'full'
  },
  {
    path : 'dashboard',
    component : DashboardComponent,
    pathMatch : 'full',
    canActivate:[UserGuard]
  },
  {
    path : 'user/edit_profile',
    component : ProfileComponent,
    pathMatch : 'full',
  },
  {
    path : 'user/profile',
    component : UserDashboardComponent,
    pathMatch : 'full',
  },
  {
    path : 'home',
    component : HomeComponent,
    pathMatch : 'full',
  },
  {
    path : 'publication',
    component : CreatePostComponent,
    pathMatch : 'full',
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
