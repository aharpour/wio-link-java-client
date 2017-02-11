import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { GroveComponent } from './grove.component';
import { GroveDetailComponent } from './grove-detail.component';
import { GrovePopupComponent } from './grove-dialog.component';
import { GroveDeletePopupComponent } from './grove-delete-dialog.component';

import { Principal } from '../../shared';


export const groveRoute: Routes = [
  {
    path: 'grove',
    component: GroveComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'dashboardApp.grove.home.title'
    }
  }, {
    path: 'grove/:id',
    component: GroveDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'dashboardApp.grove.home.title'
    }
  }
];

export const grovePopupRoute: Routes = [
  {
    path: 'grove-new',
    component: GrovePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'dashboardApp.grove.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'grove/:id/edit',
    component: GrovePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'dashboardApp.grove.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'grove/:id/delete',
    component: GroveDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'dashboardApp.grove.home.title'
    },
    outlet: 'popup'
  }
];
