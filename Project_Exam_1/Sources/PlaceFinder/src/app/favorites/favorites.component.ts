import { Component, OnInit } from '@angular/core';
import { DataService } from '../services/data.service';
import { Favorite } from '../interfaces/favorite';

@Component({
  selector: 'app-favorites',
  templateUrl: './favorites.component.html',
  styleUrls: ['./favorites.component.css']
})
export class FavoritesComponent implements OnInit {
  favorites: Favorite[];

  constructor(private dataService: DataService) { }

  ngOnInit(): void {
    this.dataService.getFavorites('Jonathan').subscribe((data: any) => {
      this.favorites = data;
    });
  }
}
