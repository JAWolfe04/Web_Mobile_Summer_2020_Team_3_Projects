import { Component, OnInit } from '@angular/core';
import { DataService } from '../services/data.service';

@Component({
  selector: 'app-info',
  templateUrl: './info.component.html',
  styleUrls: ['./info.component.css']
})
export class InfoComponent implements OnInit {

  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    this.dataService.getCurrentVenueDetails().subscribe((details: any) => {
      console.log(details);
    });
  }

  addFavorite() {
    const favorite = {
      user: 'Jonathan',
      name: 'test',
      id: '12345678'
    }

    this.dataService.addFavorite(favorite).subscribe(
      async error => { console.log(error); });
  }
}
