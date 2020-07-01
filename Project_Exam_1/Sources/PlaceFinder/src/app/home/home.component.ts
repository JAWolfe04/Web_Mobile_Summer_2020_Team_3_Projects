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

  header: any;
  venueList = [];
  currentLat: any;
  currentLong: any;
  geolocationPosition: any;

  constructor(private dataService: DataService) {
    this.venue = 'museum';
    this.location = 'Kansas City, KS';

    this.header = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + environment.yelp_key
    });
  }

  ngOnInit() {
    // document.body.classList.add('bg-img');
    window.navigator.geolocation.getCurrentPosition(
      position => {
        this.geolocationPosition = position;
        this.currentLat = position.coords.latitude;
        this.currentLong = position.coords.longitude;
      });
  }

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