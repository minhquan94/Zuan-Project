import { ItemAdminDetail } from './item-navbar-admin-detail';

export class ItemNavbarAdmin {

    public count: Number;
    constructor(public title: string, public itemDetails: ItemAdminDetail[], public group: Number) {
    }
}
