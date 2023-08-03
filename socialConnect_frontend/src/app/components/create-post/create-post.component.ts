import { Component, Input, OnInit } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { rejects } from 'assert';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {

  public publication = {
    user : {
      id:'',
      email: ''
    },
    date: '',
    title: '',
    body: '',
    picture: ''
  }

  private srcResult:any;

  constructor(private userService:UserService,private snack:MatSnackBar, private loginService:LoginService, private router:Router) { }

  ngOnInit(): void {
  }

  changeFileChooser(event:any) {
    console.log("Event chageFile: ",event.target.files);
  }

  //Este metodo debe manejarse como un evento mediante un EventEmitter
  newPublication(){
    this.publication.date=this.hourPublication();
    //let user:any = localStorage.getItem('user');
    let user = this.loginService.getUser();
    this.publication.user.id = user.id;
    this.publication.user.email = user.email;
    console.log(this.publication);
    console.log("llamando al servicio publicación");
    this.userService.addPublication(this.publication).subscribe((data:any) => {
      console.log("respuesta addPost: ",data);
      this.loginService.getCurrentUser().subscribe((user:any) =>{
        this.loginService.setUser(user);
        console.log(user);
        this.loginService.setUser(this.userService.getUser(this.publication.user.email));
        this.router.navigate(['/user/profile',this.userService.getUser(this.publication.user.email)]);

      })

    },(error: any) => {
      console.log(error);
      this.snack.open('Post invalid, try again !!', 'Acept',{
        duration:3000
      })
    }
    )
  }

  private hourPublication(): string{
      // crea un nuevo objeto `Date`
    let today:Date = new Date();
    let date:Date = new Date();
    console.log("fecha: ",date);
    
    // obtener la fecha y la hora
    let options:any={
      year:"numeric", month:"numeric", day:"numeric", 
      hour:"numeric", minute:"numeric", second:"numeric"
    }
    let now = today.toLocaleString('es-es',{year:'numeric',month:'numeric', day:"numeric", 
                                            hour:"numeric", minute:"numeric", second:"numeric"});
    console.log(now);
    now = now.replace('/','-');
    now = now.replace(',','');
    console.log(now);
    return date.toUTCString();
    /*
        Resultado: 1/27/2020, 9:30:00 PM
      */
  }
  
  onFileSelected(): any {
    const inputNode: any = document.querySelector('#file');
  
    if (typeof (FileReader) !== 'undefined') {

      return new Promise((resolve,reject) =>{
        const reader = new FileReader();
        reader.onload = (e: any) => {
          //this.srcResult = e.target.result;
          resolve(e.target.result)
        };
        reader.readAsArrayBuffer(inputNode.files[0]);
      })
    }
  }

  cargaImg(){
    this.onFileSelected().then((result:any) => {
      this.publication.picture=this._arrayBufferToBase64(result);
    }).catch((err:any) => {
      
    });
  }

  private _arrayBufferToBase64( buffer:any ) {
    var binary = '';
    var bytes = new Uint8Array( buffer );
    var len = bytes.byteLength;
    for (var i = 0; i < len; i++) {
        binary += String.fromCharCode( bytes[ i ] );
    }
    let text = "data:image/png;base64,";
    //console.log(text.concat(window.btoa(binary)));
    return text.concat(window.btoa(binary));
}

}
