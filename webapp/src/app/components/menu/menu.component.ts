import { Subscription } from 'rxjs';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {
  MenuController,
  AlertController,
  LoadingController,
} from '@ionic/angular';
import { SessionService } from 'src/app/services/session.service';
import { MRoute } from 'src/app/app-routing.module';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.scss'],
})
export class MenuComponent implements OnInit, OnDestroy {
  private subscriptions: Subscription[] = [];

  menuItems: MRoute[] = [];

  constructor(
    private menu: MenuController,
    private alertController: AlertController,
    private loadingController: LoadingController,
    private sessionService: SessionService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.subscriptions.push(
      this.sessionService.menuItems().subscribe({
        next: (items) => (this.menuItems = items),
      })
    );
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((sub) => sub.unsubscribe());
  }

  onMenuClick(menuItem: MRoute) {
    this.navigateTo(
      `Going to ${menuItem.path}`,
      this.router.navigate([menuItem.path])
    );
  }

  private navigateTo(message: string, executeNatigation: Promise<boolean>) {
    this.loadingController
      .create({
        message,
      })
      .then((loading) => {
        loading.present();
        this.menu.close('mainMenu').then(() => {
          executeNatigation.finally(() => {
            loading.dismiss();
          });
        });
      });
  }

  logOut() {
    this.alertController
      .create({
        message: 'Are you sure?',
        buttons: [
          {
            text: 'No',
            role: 'close',
            handler: () => {
              this.menu.close('mainMenu');
            },
          },
          {
            text: 'Yes',
            handler: () => {
              this.navigateTo(
                'Getting out...',
                new Promise((resolve, reject) => {
                  this.sessionService
                    .logOut()
                    .finally(() =>
                      this.router
                        .navigate(['login'])
                        .then(resolve)
                        .catch(reject)
                    );
                })
              );
            },
          },
        ],
      })
      .then((alert) => alert.present());
  }
}
