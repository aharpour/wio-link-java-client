import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { EventHandler } from './event-handler.model';
import { EventHandlerPopupService } from './event-handler-popup.service';
import { EventHandlerService } from './event-handler.service';

@Component({
    selector: 'jhi-event-handler-delete-dialog',
    templateUrl: './event-handler-delete-dialog.component.html'
})
export class EventHandlerDeleteDialogComponent {

    eventHandler: EventHandler;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private eventHandlerService: EventHandlerService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['eventHandler', 'langauge']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.eventHandlerService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'eventHandlerListModification',
                content: 'Deleted an eventHandler'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-event-handler-delete-popup',
    template: ''
})
export class EventHandlerDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private eventHandlerPopupService: EventHandlerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.eventHandlerPopupService
                .open(EventHandlerDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
