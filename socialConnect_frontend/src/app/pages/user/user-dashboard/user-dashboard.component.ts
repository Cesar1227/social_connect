import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { Iuser } from 'src/app/model/iuser';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css']
})
export class UserDashboardComponent implements OnInit {

  @Input() public createPost_isVisible: boolean = true;

  @Input() public user_nickName: string = "";

  @Input() public user_to_visit: Iuser | undefined;

  @Input() public to_visit: boolean = false;

  @Input() public current_user: string = "";

  width_imgs: string = '100px';
  height_imgs: string = '100px';

  public user: Iuser = {
    password: '',
    name: '',
    lastName: '',
    email: '',
    profile: {
      nickName: '',
      cellphone: '',
      profile: '',
      follows: Array()
    },
    publications: Array()
  }

  constructor(private loginService: LoginService, private rutaActiva: ActivatedRoute, private userService: UserService) { }

  ngOnInit(): void {
    this.ngOnChanges();
  }

  ngOnChanges() {
    this.foundAndVisualizeUsers();
  }

  //MANEJAR CUALQUIER USUARIO, HACER LA PETICIÓN Y MANEJAR MEDIANTE DE MANERA SINCRONA (MEDIANTE PROMESA)
  public foundAndVisualizeUsers() {
    if (!this.to_visit) {
      this.setUser();
    } else {
      if (this.user_nickName != undefined || this.user_nickName != null) {
        if (this.user_nickName.trim() != "") {
          console.log("is null? " + typeof (this.userService));
          /*this.userService.getUserDBByNickName(this.user_nickName).then((result: any) => {
            this.user = result;
          }).catch((err:any) => {
            Swal.fire('User not found', 'Message', 'info');
          });
          console.log(this.user.email);*/

          this.userService.getUser(this.user_nickName).subscribe((data: any) => {
            // Aquí recibimos los datos del usuario y asignamos a la variable "user"
            this.user = data;
          },
            (error: any) => {
              // Manejo de errores si la petición a la API falla
              Swal.fire('User not found', 'Message', 'info');
            }
          );
          /*let user_recived = this.userService.getUserDBByNickName(this.user_nickName);
          if (user_recived != null) {
            this.user = user_recived;
          }else{
            Swal.fire('User not found','Message','info');
          }*/
        } else {
          Swal.fire('User_field empty', 'Message', 'info');
        }


        //this.user=this.user_to_visit;
      } else {
        Swal.fire('User not found user to visit undefined or null', 'Has ocurred a problem', 'error');
      }
    }
  }

  public getUserByNickName() {
    let user_recived = this.userService.getUserDBByNickName(this.user_nickName);
    if (user_recived != null) {
      this.user = user_recived;
    } else {
      Swal.fire('User not found', 'Has ocurred a problem', 'error');
    }

  }

  public getUser(userEmail: string) {
    this.user = this.userService.getUserDB(userEmail);
  }

  public setUser() {
    let userSession = sessionStorage.getItem('userView');

    if (userSession == null) {
      console.log("no hay user - user dashboard");

      let localstora = localStorage.getItem('user');
      if (localstora != null) {
        this.user = JSON.parse(localstora);
      } else {
        Swal.fire('User not found', 'Has ocurred a problem', 'error');
      }
    } else {
      this.user = JSON.parse(userSession);
      sessionStorage.removeItem('userView');
      //this.fixedImgs();
    }
  }

  fixedImgs() {
    let img: string;
    for (let item in this.user.publications) {
      img = this.user.publications[item].img;
      img.replace('data:image/', '');
      this.user.publications[item].img = img;
    }
    console.log(this.user.publications[0].img);
  }

  public isCurrentUser() {
    return this.loginService.isMyProfile(this.user);
  }

  public isFollowed() {
    let data_CurrentUser;
    this.userService.getUserDB(this.current_user).subscribe((data: any) => {
      data_CurrentUser = data;
    },
      (error: any) => {
        // Manejo de errores si la petición a la API falla
        //Swal.fire('User not found', 'Message', 'info');
      }
    );
    
  }
}
