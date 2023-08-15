import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserDashboardComponent } from 'src/app/pages/user/user-dashboard/user-dashboard.component';
import { LoginService } from 'src/app/services/login.service';


@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {

  constructor(public loginService:LoginService, public router:Router) { }

  public userName:string | null = null;

  async ngOnInit(): Promise<void> {
    let user = await this.loginService.getUser();
    this.userName= user.name;
  }

  public logout(){
    this.loginService.logout();
    this.router.navigate(['login']);
  }

  setUser_viewProfile(){
    let user = this.loginService.getUser();
    user = user;
    sessionStorage.setItem('userView',JSON.stringify(user));
    this.router.navigate(['user/profile',user.profile.nickName]);
  }

}
