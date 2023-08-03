export interface Iuser {
        password : '';
        name : '';
        lastName : '';
        email : '';
        profile : {
          nickName : '',
          cellphone : '',
          profile:'',
          follows: Array<any>
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
