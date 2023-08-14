import { FILE } from "dns";

export interface Iuser {
  password : '';
  name : '';
  lastName : '';
  email : '';
  profile : {
    nickName : '',
    cellphone : '',
    profile:'',
    follows: Array<any>,
    urlPhoto:''
  };
  publications: Array<any>;
}

export class Cuser{
/*password : string;
name : string;
lastName : string;
email : string;
profile : {
nickName : string,
cellphone : string,
profile:string
};
publications: Array<any>;*/
constructor(password:string, name:string, lastname:String,email : string, profile : {nickName : string,cellphone : string,profile:string}, publications: Array<any>){

}
}

/*
export interface Iuser {
        password : '';
        name : '';
        lastName : '';
        email : '';
        profile : {
          nickName : '',
          cellphone : '',
          profile: File | null | string,
          follows: Array<any>
        };
        publications: Array<any>;
}

export class Cuser{
    password : string ='';
    name : string = '';
    lastName : string = '';
    email : string = '';
    profile : {
      nickName : string,
      cellphone : string,
      profile:File | null,
      follows:Array<any> | null
    } = {nickName: '',cellphone:'',profile:null,follows:null};
    publications: Array<any> | null = null;

    /*constructor(password:string, name:string, lastname:string,email : string, profile : {nickName : string,cellphone : string,profile:File}, publications: Array<any>){
      this.password=password;
      this.name=name;
      this.lastName=lastname;
      this.email= email;
      this.profile = profile;
      this.publications = publications;
    }*/

//}
