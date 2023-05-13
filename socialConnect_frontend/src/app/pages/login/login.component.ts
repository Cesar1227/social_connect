import { Component, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { LoginService } from 'src/app/services/login.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginData = {
    "email" : '',
    "password" : ''
  }

  constructor(private snack:MatSnackBar, private loginService:LoginService, private router:Router) { }

  ngOnInit(): void {
  }

  formSubmit(){
    if(this.loginData.email.trim()== '' || this.loginData.email.trim == null){
      this.snack.open('El email es requerido !!','Aceptar',{
        duration:3000
      })
      return;
    }

    if(this.loginData.password.trim()== '' || this.loginData.password.trim == null){
      this.snack.open('La contraseña es requerida !!','Aceptar',{
        duration:3000
      })
      return;
    }

    this.loginService.generateToken(this.loginData).subscribe(
      (data:any)=>{
        console.log(data);
        this.loginService.loginUser(data.token);
        this.loginService.getCurrentUser().subscribe((user:any) =>{
          this.loginService.setUser(user);
          console.log(user);

          if(user!=null){
            console.log("user != null - login.components.ts");
            this.router.navigate(['/dashboard']);
            //window.location.href = '/dashboard';
            this.loginService.loginStatusSubjec.next(true);
          }else{
            console.log("user === null - login.components.ts, haciendo logout");
            this.loginService.logout();
          }
        })
      },(error: any) => {
        console.log(error);
        this.snack.open('Details invalid, try again !!', 'Acept',{
          duration:3000
        })
      }
    )
    
  }

}
