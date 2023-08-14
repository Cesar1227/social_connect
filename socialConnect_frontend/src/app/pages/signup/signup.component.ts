import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { Cuser } from 'src/app/model/iuser';
import { UserService } from 'src/app/services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

  public user = {
    password: '',
    name: '',
    lastName: '',
    email: '',
    profile: {
      nickName: '',
      cellphone: '',
      follows: Array(),
      urlPhoto:''
    }
    ,
    publications: []
  }

  public profilePicture: FileList | null = null;

  public currentPicture: File | null = null;

  private nErrorSignup:number = 0;

  //user2:Cuser = new Cuser();

  constructor(private userServices: UserService, private snack: MatSnackBar, private router: Router) { }

  ngOnInit(): void {
  }

  formSubmit() {
    console.log(this.user);
    
    if (this.user.profile.nickName == '' || this.user.profile.nickName == null) {
      this.snack.open('Username is required', 'Accept', {
        duration: 3000,
        verticalPosition: 'top',
        horizontalPosition: 'right'
      })
      //alert('Username is required');
      return;
    }
   
    if (this.profilePicture !=null && this.profilePicture!=undefined) {
      this.currentPicture = this.profilePicture.item(0);
      if(this.currentPicture!=null){
        this.userServices.addUserWithPicture(this.user,this.currentPicture).then( ((resp:any) =>{
          this.router.navigate(['login']);
        }),(error:any)=>{
          console.error(error);
          Swal.fire('User not saved', 'An error has occurred, try again', 'error');
          this.nErrorSignup+=1;
          if(this.nErrorSignup>=3){
            Swal.fire('Error', 'If the problem persists, please contact support.', 'error');
          }
          return;
        });
        
      }
    }else{
      this.userServices.addUser(this.user).subscribe(
        ((data) => {
          console.log(data);
          Swal.fire('User saved', 'User saved successfully', 'success');
          /*this.snack.open('User save successfully','Accept',{
            duration : 3000,
            verticalPosition : 'top',
            horizontalPosition : 'right'
          })*/
          //alert('User save successfully');
          this.router.navigate(['login']);
        }), (error) => {
          console.error(error);
          this.snack.open('An error has occurred in the system', 'Accept', {
            duration: 3000,
            verticalPosition: 'top',
            horizontalPosition: 'right'
          })
        }
      )
    }
  }

  changedImage(event:any) {
    this.profilePicture = event.target.files[0];
  }

  uploadImg(event: any) {
    this.profilePicture = event.target.files;
  }
}
