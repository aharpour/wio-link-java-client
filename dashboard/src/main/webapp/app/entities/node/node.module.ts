import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { DashboardSharedModule } from '../../shared';

import {
    NodeService,
    NodePopupService,
    NodeComponent,
    NodeDetailComponent,
    NodeDialogComponent,
    NodePopupComponent,
    NodeDeletePopupComponent,
    NodeDeleteDialogComponent,
    nodeRoute,
    nodePopupRoute,
} from './';

let ENTITY_STATES = [
    ...nodeRoute,
    ...nodePopupRoute,
];

@NgModule({
    imports: [
        DashboardSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        NodeComponent,
        NodeDetailComponent,
        NodeDialogComponent,
        NodeDeleteDialogComponent,
        NodePopupComponent,
        NodeDeletePopupComponent,
    ],
    entryComponents: [
        NodeComponent,
        NodeDialogComponent,
        NodePopupComponent,
        NodeDeleteDialogComponent,
        NodeDeletePopupComponent,
    ],
    providers: [
        NodeService,
        NodePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class DashboardNodeModule {}
