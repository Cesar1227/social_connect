import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { UserService } from 'src/app/services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

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
    publications: []
  }

  constructor(private userService:UserService, private snack:MatSnackBar, private router:Router) { 
    let varLocal: string | null = localStorage.getItem('user');
    if(varLocal!=null){
      this.user = JSON.parse(varLocal);
    }
  }

  ngOnInit(): void {
  }

  formSubmit(){

  }

}
