import { Router } from '@angular/router';
import { Component } from '@angular/core';
import {
  AlertController,
  LoadingController,
  MenuController,
} from '@ionic/angular';
import { SessionService } from './services/session.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {

  constructor(
    private menu: MenuController,
    private alertController: AlertController,
    private loadingController: LoadingController,
    private sessionService: SessionService,
    private router: Router
  ) {}

  logOut() {
    this.alertController.create({
      message: 'Are you sure?',
      buttons: [
        {
          text: 'No',
          role: 'close',
          handler: ()=>{
            this.menu.close('mainMenu');
          }
        },
        {
          text: 'Yes',
          handler: () => {
            this.loadingController
              .create({
                message: 'Getting out...',
              })
              .then((loading) => {
                loading.present();
                this.sessionService.logOut().finally(() => {
                  this.menu.close('mainMenu').then(() => {
                    this.router.navigate(['login']).finally(() => {
                      loading.dismiss();
                    });
                  });
                });
              });
          },
        },
      ],
    }).then(alert=>alert.present());
  }

}
