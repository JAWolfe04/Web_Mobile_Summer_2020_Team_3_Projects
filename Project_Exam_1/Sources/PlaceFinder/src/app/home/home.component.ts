import { Component, OnInit } from '@angular/core';
import { DataService } from '../services/data.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  venue: string;
  location: string;
  results: any;

  constructor(private dataService: DataService) {
    this.venue = 'museum';
    this.location = 'Kansas City, KS';
  }

  ngOnInit(): void { }

  // Find a list of venues that matches the location and venue description
  searchVenues() {
    if (this.location != null && this.location !== '' && this.venue != null && this.venue !== '') {
      this.dataService.getVenues(this.location, this.venue).subscribe((data: any) => {
        this.results = data.response.venues;
        console.log(this.results);
      });
    }
  }

  // Pass the index of the selected item from the results list
  getDetails(index) {
    this.dataService.getDetails(this.results[index]);
  }
}
