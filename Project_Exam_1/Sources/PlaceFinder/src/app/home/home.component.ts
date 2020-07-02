import { Component, OnInit } from '@angular/core';
import { DataService } from '../services/data.service';
import {HttpHeaders} from '@angular/common/http';
import {environment} from '../../environments/environment';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  venue: string;
  location: string;
  venueList = [];
  currentLat: any;
  currentLong: any;
  geolocationPosition: any;

  constructor(private dataService: DataService) {
    this.venue = 'museum';
    this.location = 'Kansas City, KS';
  }

  ngOnInit() {
    document.body.classList.add('bg-img');
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
        this.venueList = data.response.venues;
      });
    }
  }

  // Pass the index of the selected item from the results list
  getDetails(index) {
    this.dataService.getVenueID(this.venueList[index]);
  }
}
