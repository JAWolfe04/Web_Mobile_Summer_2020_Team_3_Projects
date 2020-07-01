import { Component, OnInit } from '@angular/core';
import { DataService } from '../services/data.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-info',
  templateUrl: './info.component.html',
  styleUrls: ['./info.component.css']
})
export class InfoComponent implements OnInit {
  info: any;

  constructor(private dataService: DataService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.dataService.getVenueDetails(this.route.snapshot.params.id)
      .subscribe((details: any) => {
        this.info = details;
    });
  }

  addFavorite() {
    this.dataService.addFavorite({
      id: this.route.snapshot.params.id,
      name: this.info.name
    }).subscribe();
  }
}
