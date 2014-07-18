class FIXML {
    Order Order_object = new Order();
    Hdr Hdr_object = new Hdr();

    FIXML() {
    }

    FIXML(Order Order_, Hdr Hdr_) {
	this.Order_object = Order_;
	this.Hdr_object = Hdr_;
    }
}

class Order {
    String ClOrdID = "123456";
    String Side = "2";
    String TransactTm = "2001-09-11T09:30:47-05:00";
    String OrdTyp = "2";
    String Px = "93.25";
    String Acct = "26522154";

    Order() {
    }

    Order(String ClOrdID, String Side, String TransactTm, String OrdTyp,
	    String Px, String Acct) {
	this.ClOrdID = ClOrdID;
	this.Side = Side;
	this.TransactTm = TransactTm;
	this.OrdTyp = OrdTyp;
	this.Px = Px;
	this.Acct = Acct;
    }
}

class Hdr {
    String Snt = "2001-09-11T09:30:47-05:00";
    String PosDup = "N";
    String PosRsnd = "N";
    String SeqNum = "521";
    Sndr Sndr_object = new Sndr();
    Instrmt Instrmt_object = new Instrmt();
    OrdQty OrdQty_object = new OrdQty();

    Hdr() {
    }

    Hdr(String Snt, String PosDup, String PosRsnd, String SeqNum, Sndr Sndr_,
	    Instrmt Instrmt_, OrdQty OrdQty_) {
	this.Snt = Snt;
	this.PosDup = PosDup;
	this.PosRsnd = PosRsnd;
	this.SeqNum = SeqNum;
	this.Sndr_object = Sndr_;
	this.Instrmt_object = Instrmt_;
	this.OrdQty_object = OrdQty_;
    }
}

class Sndr {
    String ID = "AFUNDMGR";
    Tgt Tgt_object = new Tgt();

    Sndr() {
    }

    Sndr(String ID, Tgt Tgt_) {
	this.ID = ID;
	this.Tgt_object = Tgt_;
    }
}

class Instrmt {
    String Sym = "IBM";
    String ID = "459200101";
    String IDSrc = "1";

    Instrmt() {
    }

    Instrmt(String Sym, String ID, String IDSrc) {
	this.Sym = Sym;
	this.ID = ID;
	this.IDSrc = IDSrc;
    }
}

class OrdQty {
    String Qty = "1000";

    OrdQty() {
    }

    OrdQty(String Qty) {
	this.Qty = Qty;
    }
}

class Tgt {
    String ID = "ABROKER";

    Tgt() {
    }

    Tgt(String ID) {
	this.ID = ID;
    }
}