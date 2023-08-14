import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { lastValueFrom } from 'rxjs/internal/lastValueFrom';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {

  public user = {
    password: '',
    name: '',
    lastName: '',
    email: '',
    profile: {
      nickName: '',
      cellphone: '',
      profile: '',
      urlPhoto: '',
      follows: Array()
    },
    publications: []
  }

  public profilePicture: FileList | null = null;

  public currentPicture: File | null = null;

  private nErrorSignup: number = 0;

  constructor(private userServices: UserService, private snack: MatSnackBar, private router: Router, private loginService: LoginService) {
    let varLocal: string | null = sessionStorage.getItem('user');
    if (varLocal != null) {
      this.user = JSON.parse(varLocal);
    }
  }

  ngOnInit(): void {
  }

  formSubmit() {

    if (this.user.profile.nickName == '' || this.user.profile.nickName == null) {
      this.snack.open('Username is required', 'Accept', {
        duration: 3000,
        verticalPosition: 'top',
        horizontalPosition: 'right'
      })
      //alert('Username is required');
      return;
    }

    if (this.profilePicture != null && this.profilePicture != undefined) {
      this.currentPicture = this.profilePicture.item(0);
      if (this.currentPicture != null) {
        this.userServices.addUserWithPicture(this.user, this.currentPicture, "update").then((async (user: any) => {
          let response:Map<any,any>=await user;
          let userRecived = response.get("user");
          //userRecived.profile=response.get("profile");
          this.loginService.setUser(this.loginService.updateCurrentUser());
          Swal.fire('User updated', 'User updated successfully', 'success');

        }),(reason:any)=>{
          console.error(reason);
          Swal.fire('User not updated', 'An error has occurred, try again', 'error');
          this.nErrorSignup += 1;
          if (this.nErrorSignup >= 3) {
            Swal.fire('Error', 'If the problem persists, please contact support.', 'error');
          }
          return;
        });
        
        //this.loginService.updateCurrentUser();
        this.router.navigate(['dashboard']);
      }
    } else {
      this.updateUser(this.user).then((async (data: any) => {

        //let data2 = await lastValueFrom(data);
        this.loginService.setUser(await (data));
        //console.log(data);
        Swal.fire('User updated', 'User updated successfully', 'success');
        /*this.snack.open('User save successfully','Accept',{
          duration : 3000,
          verticalPosition : 'top',
          horizontalPosition : 'right'
        })*/
        //alert('User save successfully');
        //this.loginService.updateCurrentUser();
        this.router.navigate(['dashboard']);
      
      }),(error:any)=>{
        console.error(error);
        this.snack.open('An error has occurred in the system', 'Accept', {
          duration: 3000,
          verticalPosition: 'top',
          horizontalPosition: 'right'
        })
      });
    }
  }

  async updateUser(user: any) {
    let data$ = this.userServices.updateUser(user);
    let data = await lastValueFrom(data$);
    return Promise.resolve(data);
  }

  changedImage(event: any) {
    this.profilePicture = event.target.files[0];
  }

  uploadImg(event: any) {
    this.profilePicture = event.target.files;
  }
}
