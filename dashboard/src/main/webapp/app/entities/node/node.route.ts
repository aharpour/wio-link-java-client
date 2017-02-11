import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { NodeComponent } from './node.component';
import { NodeDetailComponent } from './node-detail.component';
import { NodePopupComponent } from './node-dialog.component';
import { NodeDeletePopupComponent } from './node-delete-dialog.component';

import { Principal } from '../../shared';


export const nodeRoute: Routes = [
  {
    path: 'node',
    component: NodeComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'dashboardApp.node.home.title'
    }
  }, {
    path: 'node/:id',
    component: NodeDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'dashboardApp.node.home.title'
    }
  }
];

export const nodePopupRoute: Routes = [
  {
    path: 'node-new',
    component: NodePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'dashboardApp.node.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'node/:id/edit',
    component: NodePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'dashboardApp.node.home.title'
    },
    outlet: 'popup'
  },
  {
    path: 'node/:id/delete',
    component: NodeDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'dashboardApp.node.home.title'
    },
    outlet: 'popup'
  }
];
