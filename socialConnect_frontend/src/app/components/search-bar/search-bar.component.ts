import { Component, EventEmitter, OnInit, Output } from '@angular/core';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css']
})
export class SearchBarComponent implements OnInit {

  @Output() paginaEmitter: EventEmitter<string> =  new EventEmitter();

  constructor() { }

  value = '';

  ngOnInit(): void {
  }

  buscar(){
    console.log("[search-bar] A buscar: "+this.value)
    this.paginaEmitter.emit(this.value);
  }

}
