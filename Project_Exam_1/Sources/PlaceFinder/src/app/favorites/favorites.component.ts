import { Component, OnInit } from '@angular/core';
import { DataService } from '../services/data.service';

@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.css']
})
export class FavoritesComponent implements OnInit {
  favorites: any;

  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    this.dataService.getFavorites(this.dataService.user).subscribe((data: any) => {
      this.favorites = data;
    });
  }
}
