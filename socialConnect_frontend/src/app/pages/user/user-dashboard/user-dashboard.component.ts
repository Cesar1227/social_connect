import { Component, OnInit } from '@angular/core';
import { LoginService } from 'src/app/services/login.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css']
})
export class UserDashboardComponent implements OnInit {

  width_imgs:string = '100px';
  height_imgs:string = '100px';

  public user = {
    password : '',
    name : '',
    lastName : '',
    email : '',
    profile : {
      nickName : '',
      cellphone : '',
      profile:''
    },
    publications: Array()
  }


  constructor(private loginService:LoginService) {
    
  }

  ngOnInit(): void {
    this.setUser();
  }

  public setUser(){
    let userSession = sessionStorage.getItem('userView');

    if(userSession==null){
      console.log("no hay user - user dashboard");
      Swal.fire('User not found','Has ocurred a problem','error');
    }else{
      this.user = JSON.parse(userSession);
      sessionStorage.removeItem('userView');
      //this.fixedImgs();
    }
  }

  fixedImgs(){
    let img:string;
    for(let item in this.user.publications){
        img=this.user.publications[item].img;
        img.replace('data:image/','');
        this.user.publications[item].img=img;
    }
    console.log(this.user.publications[0].img);
  }

  public isCurrentUser(){
    return this.loginService.isMyProfile(this.user);
  }
}
