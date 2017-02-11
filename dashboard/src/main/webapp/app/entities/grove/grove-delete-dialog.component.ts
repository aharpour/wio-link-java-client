import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Grove } from './grove.model';
import { GrovePopupService } from './grove-popup.service';
import { GroveService } from './grove.service';

@Component({
    selector: 'jhi-grove-delete-dialog',
    templateUrl: './grove-delete-dialog.component.html'
})
export class GroveDeleteDialogComponent {

    grove: Grove;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private groveService: GroveService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['grove']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.groveService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'groveListModification',
                content: 'Deleted an grove'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-grove-delete-popup',
    template: ''
})
export class GroveDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private grovePopupService: GrovePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.grovePopupService
                .open(GroveDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
