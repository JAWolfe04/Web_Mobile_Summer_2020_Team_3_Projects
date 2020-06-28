import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';

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

  constructor(private http: HttpClient) {
    this.venue = 'museum';
    this.location = 'Kansas City, KS';
    this.header = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + environment.yelp_key });
  }

  ngOnInit(): void {
  }

  searchPlace() {
    if (this.location != null && this.location !== '' && this.venue != null && this.venue !== '') {
      this.http.get('https://api.foursquare.com/v2/venues/search?query=' + this.venue +
        '&near=' + this.location +
        '&limit=5' +
        '&client_id=' + environment.foursquare_id +
        '&client_secret=' + environment.foursquare_secret +
        '&v=20200626')
        .subscribe((data: any) => {
          console.log(data);
          this.results = data.response.venues;
          this.getReview();
        });
    }
  }

  getReview() {
    const i = 0;
    this.http.get('https://cors-anywhere.herokuapp.com/https://api.yelp.com/v3/businesses/matches?name=' + this.results[i].name +
      '&address1=' + this.results[i].location.address +
      '&city=' + this.results[i].location.city +
      '&state=' + this.results[i].location.state +
      '&country=' + this.results[i].location.cc +
      '&limit=5', { headers: this.header })
      .subscribe((business: any) => { this.getDetails(business.businesses[0].id); });
  }

  getDetails(id) {
    this.http.get('https://cors-anywhere.herokuapp.com/https://api.yelp.com/v3/businesses/' +
      id, { headers: this.header })
      .subscribe((details: any) => { console.log(details); });
  }
}
