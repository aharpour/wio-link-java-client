import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, JhiLanguageService } from 'ng-jhipster';

import { Node } from './node.model';
import { NodePopupService } from './node-popup.service';
import { NodeService } from './node.service';

@Component({
    selector: 'jhi-node-delete-dialog',
    templateUrl: './node-delete-dialog.component.html'
})
export class NodeDeleteDialogComponent {

    node: Node;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private nodeService: NodeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['node']);
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: string) {
        this.nodeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'nodeListModification',
                content: 'Deleted an node'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-node-delete-popup',
    template: ''
})
export class NodeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private nodePopupService: NodePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.nodePopupService
                .open(NodeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
