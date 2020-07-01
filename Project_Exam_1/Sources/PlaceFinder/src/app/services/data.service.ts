import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class DataService {
  private readonly header: any;
  user: any;

  constructor(private http: HttpClient, private router: Router) {
    this.header = new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: 'Bearer ' + environment.yelp_key });
  }

  loginUser(user: string) {
    if (this.user !== null && this.user !== '') {
      this.http.get(`${environment.app_server_address}/loginUser/${user}`)
        .subscribe((data: any) => {
        this.user = data._id;
        this.router.navigateByUrl('/home');
      });
    }
  }

  getFavorites(user: string): Observable<any> {
    return this.http.get<any>(`${environment.app_server_address}/getFavorites/${user}`);
  }

  addFavorite(favorite: any) {
    return this.http.post(`${environment.app_server_address}/addFavorite/${this.user}`, favorite);
  }

  getVenues(location, venue) {
    return this.http.get('https://api.foursquare.com/v2/venues/search?query=' + venue +
      '&near=' + location +
      '&limit=5' +
      '&client_id=' + environment.foursquare_id +
      '&client_secret=' + environment.foursquare_secret +
      '&v=20200626');
  }

  getVenueID(venue) {
    return this.http.get('https://cors-anywhere.herokuapp.com/https://api.yelp.com/v3/businesses/matches' +
      '?name=' + venue.name +
      '&address1=' + venue.location.address +
      '&city=' + venue.location.city +
      '&state=' + venue.location.state +
      '&country=' + venue.location.cc +
      '&limit=5', { headers: this.header }).subscribe((business: any) => {
        if (business.businesses.length === 0) {
          console.log('No information on this location');
        } else {
          this.router.navigateByUrl(`/info/${business.businesses[0].id}`);
        }
    });
  }

  getVenueDetails(id) {
    return this.http.get('https://cors-anywhere.herokuapp.com/https://api.yelp.com/v3/businesses/' +
      id, { headers: this.header });
  }
}
