import { Component, Input, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ActivatedRoute, ParamMap } from '@angular/router';
import { Console } from 'console';
import { lastValueFrom } from 'rxjs';
import { Iuser } from 'src/app/model/iuser';
import { LoginService } from 'src/app/services/login.service';
import { ProfileService } from 'src/app/services/profile.service';
import { UserService } from 'src/app/services/user.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-user-dashboard',
  templateUrl: './user-dashboard.component.html',
  styleUrls: ['./user-dashboard.component.css']
})
export class UserDashboardComponent implements OnInit {

  @Input() public createPost_isVisible: boolean = true;

  @Input() public user_nickName_to_visit: string = "";

  @Input() public user_to_visit: Iuser | undefined;

  @Input() public to_visit: boolean = false;

  @Input() public nick_current_user: string = "";

  public is_followed: boolean = false;

  width_imgs: string = '100px';
  height_imgs: string = '100px';

  /**
   * Usuario del que se va a mostrar el perfil
   * En caso de ser el perfil del usuario el mismo usuario actual
   * En caso de la busqueda de un usuario, será el usuario visitante o a buscar
  **/
  public user: Iuser = {
    password: '',
    name: '',
    lastName: '',
    email: '',
    profile: {
      nickName: '',
      cellphone: '',
      profile: '',
      follows: Array(),
      urlPhoto: ''
    },
    publications: Array()
  }

  constructor(private snack: MatSnackBar, private loginService: LoginService, private rutaActiva: ActivatedRoute, private userService: UserService, private profileService: ProfileService) {
    //this.foundAndVisualizeUsers();
  }

  ngOnInit(): void {
    //this.ngOnChanges();

    if (!this.to_visit) {
      this.foundAndVisualizeUsers();
    }
  }

  ngOnChanges() {
    this.foundAndVisualizeUsers();
  }

  /**
   * Encuentra el perfil y establece el usuario a visualizar
   */
  public foundAndVisualizeUsers() {
    if (!this.to_visit) {
      //El usuario logueado visualiza su perfil

      this.setUser();
    } else {
      //El usuario logueado busca el perfil de otro en el dashboard
      if (this.user_nickName_to_visit != undefined && this.user_nickName_to_visit != null) {
        if (this.user_nickName_to_visit.trim() != "") {
          this.userService.getUserDB(this.user_nickName_to_visit).then(((data: any) => {
            this.user = data;
          }), (error) => {
            Swal.fire('User not found', 'Message', 'info');
            return;
          });
          this.isFollowed();
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
    let user_recived = this.userService.getUserDBByNickName(this.user_nickName_to_visit);
    if (user_recived != null) {
      this.user_to_visit = user_recived;
    } else {
      Swal.fire('User not found', 'Has ocurred a problem', 'error');
    }
  }

  /**
   * Obtiene el usuario desde la base de datos
   * @param userEmail email o nickName del usuario a buscar
   */
  public getUser(userEmail: string) {
    /*this.user = this.userService.getUserDB(userEmail);*/ //Funciona sin una promesa en userService
    let userRecived;
    this.userService.getUserDB(userEmail).then((user: any) => {
      //this.user = user;
      userRecived = user;
      if (userRecived != null && userRecived != undefined) {
        return user;
      } else {
        return null;
      }
    });
  }

  /**
   * Establecer el usuario a mostrar el perfil obtenido desde la variable de sessión
   * Caso de cuando el usuario logueado visita su perfil
   */
  public setUser() {
    //let userSession = sessionStorage.getItem('userView');
    let localstora = this.loginService.getUser();
    if (localstora != null) {
      this.user = localstora;
    } else {
      Swal.fire('User not found', 'Has ocurred a problem \/n Try again please', 'error');
    }

  }

  /**
   * Establece el formato adecuado para mostrar las imagenes de cada publicación del perfil visualizado
   * @param user1 Usuario a obtener la data de la imagen
   * 
   * El almacenamiento de las imagenes será cambiado por una base de datos en EC2 en las próximas actualizaciones
   */
  fixedImgs(user1: Iuser) {
    let img: string;
    for (let item in user1.publications) {
      img = user1.publications[item].img;
      img.replace('data:image/', '');
      user1.publications[item].img = img;
    }
    //console.log(user1.publications[0].img);
  }

  /**
   * Verifica si es el usuario actual el que quiere ver su perfil
   * @returns true si es el mismo usuario, false si no es el usuario
   */
  public isCurrentUser() {
    return this.loginService.isMyProfile(this.user);
  }

  /**
   * Verifica si el usuario actual sigue al usuario del cual se muestra el perfil
   */
  async isFollowed() {
    let data_CurrentUser: Iuser;
    (this.userService.getUserDB(this.nick_current_user)).then((result: any) => {
      if (result != null && result != undefined) {
        data_CurrentUser = result;
        let array: Array<string> = data_CurrentUser.profile.follows;
        this.is_followed = array.includes(this.user_nickName_to_visit);
      } else {
        Swal.fire('It wasn\'t possible to follow this user', 'Message', 'info');
      }
    });


  }

  followUser() {
    this.profileService.followUser(this.nick_current_user, this.user_nickName_to_visit).then(response => {
      if (response == null) {
        Swal.fire('It wasn\'t possible to follow this user', 'Message', 'info');
      } else {
        this.is_followed = true;
        let userRecived = this.loginService.updateCurrentUser();
        //this.loginService.setUser(this.getUser(this.nick_current_user));
        this.snack.open('Now, you have another connection', '', {
          duration: 3000
        })
      }
    })

    //Swal.fire('Now, you have another connection', 'Message', 'success');
  }

  stopFollowing() {
    /*console.log("vas a dejar de seguir a: " + this.user.profile.nickName + "|" + this.user_nickName_to_visit
      + " desde: " + this.nick_current_user);*/
    this.profileService.stopFollowingUser(this.nick_current_user, this.user_nickName_to_visit).then(response => {
      if (response == null) {
        Swal.fire('It wasn\'t possible to stop following this user', 'Message', 'info');
      } else {
        this.is_followed = false;
        //this.loginService.setUser(this.getUser(this.nick_current_user));
        let userRecived = this.loginService.updateCurrentUser();
        this.snack.open('Now, you has lost a connection', '', {
          duration: 3000
        })
      }
    })
    //Swal.fire('Now, you have another connection', 'Message', 'success');
  }

}
