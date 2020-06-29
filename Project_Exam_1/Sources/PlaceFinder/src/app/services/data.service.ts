import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Favorite } from '../interfaces/favorite';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  private readonly header: any;
  private user: string;
  private venueID: string;

  constructor(private http: HttpClient, private router: Router) {
    this.header = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + environment.yelp_key });
  }

  loginUser(user: string) {
    if (this.user !== null && this.user !== '') {
      this.user = user;
      this.router.navigateByUrl('/home');
    }
  }

  getFavorites(user: string): Observable<Favorite[]> {
    return this.http.get<Favorite[]>(`${environment.app_server_address}/getFavorites/${user}`);
  }

  addFavorite(favorite: Favorite) {
    return this.http.post(`${environment.app_server_address}/addFavorite`, favorite);
  }

  getVenues(location, venue) {
    return this.http.get('https://api.foursquare.com/v2/venues/search?query=' + venue +
      '&near=' + location +
      '&limit=5' +
      '&client_id=' + environment.foursquare_id +
      '&client_secret=' + environment.foursquare_secret +
      '&v=20200626');
  }

  getDetails(venue) {
    this.getVenueID(venue).subscribe((business: any) => {
      this.venueID = business.businesses[0].id;
      this.router.navigateByUrl('/info');
    });
  }

  private getVenueID(venue) {
    return this.http.get('https://cors-anywhere.herokuapp.com/https://api.yelp.com/v3/businesses/matches' +
      '?name=' + venue.name +
      '&address1=' + venue.location.address +
      '&city=' + venue.location.city +
      '&state=' + venue.location.state +
      '&country=' + venue.location.cc +
      '&limit=5', { headers: this.header });
  }

  getCurrentVenueDetails() {
    return this.http.get('https://cors-anywhere.herokuapp.com/https://api.yelp.com/v3/businesses/' +
      this.venueID, { headers: this.header });
  }
}
