import { Router } from '@angular/router';
import { AlertController } from '@ionic/angular';
import { MizudoService } from './../../services/mizudo.service';
import { Token } from './../../model/Token';
import { Component, OnInit } from '@angular/core';
import { Profile } from 'src/app/model/Profile';
import { SessionService } from './../../services/session.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.scss'],
})
export class ProfileComponent implements OnInit {
  constructor(
    private sessionService: SessionService,
    private mizudoService: MizudoService,
    private alertController: AlertController,
    private router: Router
  ) {}

  profile: Profile | null = null;

  ngOnInit(): void {
    this.sessionService.getToken().subscribe({
      next: (token) => this.loadProfile(token),
      error: (error) => this.onErrorLoadProfile(error),
    });
  }

  private loadProfile(token: Token | null): void {
    if (token) {
      this.mizudoService.getProfile(token).subscribe({
        next: (value) => (this.profile = value),
        error: (error) => this.onErrorLoadProfile(error),
      });
    }
  }

  private onErrorLoadProfile(error: any): void {
    this.alertController
      .create({
        message: `Cannot load profile data: ${error}`,
        buttons:[
          {
            text:"Try to login again...",
            handler:()=>{
              this.sessionService.logOut();
              this.router.navigate(["/","login"]);
            }
          }
        ]
      })
      .then((alert) => {
        alert.present();
      });
  }

  get username(): string {
    return this.profile ? this.profile.username : '';
  }
}
