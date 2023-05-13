import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { UserService } from 'src/app/services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  public user = {
    nickName : '',
    password : '',
    name : '',
    lastName : '',
    email : '',
    cellphone : '',
    profile : ''
  }

  constructor(private userServices:UserService, private snack:MatSnackBar) { }

  ngOnInit(): void {
  }

  formSubmit(){
    console.log(this.user);
    if(this.user.nickName == '' || this.user.nickName == null){
      this.snack.open('Username is required','Accept',{
        duration : 3000,
        verticalPosition : 'top',
        horizontalPosition : 'right'
      })
      //alert('Username is required');
      return;
    }

    this.userServices.addUser(this.user).subscribe(
      (data) => {
        console.log(data);
        Swal.fire('User saved','User saved successfully','success');
        /*this.snack.open('User save successfully','Accept',{
          duration : 3000,
          verticalPosition : 'top',
          horizontalPosition : 'right'
        })*/
        //alert('User save successfully');
      }, (error) => {
        console.log(error);
        this.snack.open('An error has occurred in the system','Accept',{
          duration : 3000,
          verticalPosition : 'top',
          horizontalPosition : 'right'
        })
        //alert('An error has occurred in the system')
      }
    )
  }
}
