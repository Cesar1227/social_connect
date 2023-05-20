import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-create-post',
  templateUrl: './create-post.component.html',
  styleUrls: ['./create-post.component.css']
})
export class CreatePostComponent implements OnInit {

  public publication = {
    date: '',
    title: '',
    body: '',
    picture: ''
  }

  constructor() { }

  ngOnInit(): void {
  }

  changeFileChooser(event:any) {
    console.log(event.target.files);
  }

  newPublication(){
    
  }

}
