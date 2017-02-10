import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { EventHandlerComponent } from './event-handler.component';
import { EventHandlerDetailComponent } from './event-handler-detail.component';
import { EventHandlerPopupComponent } from './event-handler-dialog.component';
import { EventHandlerDeletePopupComponent } from './event-handler-delete-dialog.component';

import { Principal } from '../../shared';


export const eventHandlerRoute: Routes = [
  {
    path: 'event-handler',
    component: EventHandlerComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'dashboardApp.eventHandler.home.title'
    }
  }, {
    path: 'event-handler/:id',
    component: EventHandlerDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'dashboardApp.eventHandler.home.title'
    }
  }
];

export const eventHandlerPopupRoute: Routes = [
  {
    path: 'event-handler-new',
    component: EventHandlerPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'dashboardApp.eventHandler.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'event-handler/:id/edit',
    component: EventHandlerPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'dashboardApp.eventHandler.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'event-handler/:id/delete',
    component: EventHandlerDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'dashboardApp.eventHandler.home.title'
    },
    outlet: 'popup'
  }
];
