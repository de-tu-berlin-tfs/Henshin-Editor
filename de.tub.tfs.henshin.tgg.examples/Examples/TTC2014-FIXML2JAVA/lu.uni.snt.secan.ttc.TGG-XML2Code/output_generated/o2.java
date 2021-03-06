/*******************************************************************************
 * Copyright (c) 2010-2015 Henshin developers. All rights reserved. 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TU Berlin, University of Luxembourg, SES S.A.
 *******************************************************************************/
class FIXML {
	PosRpt PosRpt_object = new PosRpt();

	FIXML() {
	}

	FIXML(PosRpt PosRpt_) {
		this.PosRpt_object = PosRpt_;
	}
}

class PosRpt {
	String RptID = "541386431";
	String Rslt = "0";
	String BizDt = "2003-09-10T00:00:00";
	String Acct = "1";
	String AcctTyp = "1";
	String SetPx = "0.00";
	String SetPxTyp = "1";
	String PriSetPx = "0.00";
	String ReqTyp = "0";
	String Ccy = "USD";
	Hdr Hdr_object = new Hdr();
	Pty Pty_object = new Pty();
	Pty Pty_object = new Pty();
	Pty Pty_object = new Pty();
	Qty Qty_object = new Qty();
	Qty Qty_object = new Qty();
	Qty Qty_object = new Qty();
	Amt Amt_object = new Amt();
	Instrmt Instrmt_object = new Instrmt();

	PosRpt() {
	}

	PosRpt(String RptID, String Rslt, String BizDt, String Acct,
			String AcctTyp, String SetPx, String SetPxTyp, String PriSetPx,
			String ReqTyp, String Ccy, Hdr Hdr_, Pty Pty_, Pty Pty_, Pty Pty_,
			Qty Qty_, Qty Qty_, Qty Qty_, Amt Amt_, Instrmt Instrmt_) {
		this.RptID = RptID;
		this.Rslt = Rslt;
		this.BizDt = BizDt;
		this.Acct = Acct;
		this.AcctTyp = AcctTyp;
		this.SetPx = SetPx;
		this.SetPxTyp = SetPxTyp;
		this.PriSetPx = PriSetPx;
		this.ReqTyp = ReqTyp;
		this.Ccy = Ccy;
		this.Hdr_object = Hdr_;
		this.Pty_object = Pty_;
		this.Pty_object = Pty_;
		this.Pty_object = Pty_;
		this.Qty_object = Qty_;
		this.Qty_object = Qty_;
		this.Qty_object = Qty_;
		this.Amt_object = Amt_;
		this.Instrmt_object = Instrmt_;
	}
}

class Hdr {
	String Snt = "2001-12-17T09:30:47-05:00";
	String PosDup = "N";
	String PosRsnd = "N";
	String SeqNum = "1002";
	Sndr Sndr_object = new Sndr();
	Tgt Tgt_object = new Tgt();
	OnBhlfOf OnBhlfOf_object = new OnBhlfOf();
	DlvrTo DlvrTo_object = new DlvrTo();

	Hdr() {
	}

	Hdr(String Snt, String PosDup, String PosRsnd, String SeqNum, Sndr Sndr_,
			Tgt Tgt_, OnBhlfOf OnBhlfOf_, DlvrTo DlvrTo_) {
		this.Snt = Snt;
		this.PosDup = PosDup;
		this.PosRsnd = PosRsnd;
		this.SeqNum = SeqNum;
		this.Sndr_object = Sndr_;
		this.Tgt_object = Tgt_;
		this.OnBhlfOf_object = OnBhlfOf_;
		this.DlvrTo_object = DlvrTo_;
	}
}

class Sndr {
	String ID = "String";
	String Sub = "String";
	String Loc = "String";

	Sndr() {
	}

	Sndr(String ID, String Sub, String Loc) {
		this.ID = ID;
		this.Sub = Sub;
		this.Loc = Loc;
	}
}

class Tgt {
	String ID = "String";
	String Sub = "String";
	String Loc = "String";

	Tgt() {
	}

	Tgt(String ID, String Sub, String Loc) {
		this.ID = ID;
		this.Sub = Sub;
		this.Loc = Loc;
	}
}

class OnBhlfOf {
	String ID = "String";
	String Sub = "String";
	String Loc = "String";

	OnBhlfOf() {
	}

	OnBhlfOf(String ID, String Sub, String Loc) {
		this.ID = ID;
		this.Sub = Sub;
		this.Loc = Loc;
	}
}

class DlvrTo {
	String ID = "String";
	String Sub = "String";
	String Loc = "String";

	DlvrTo() {
	}

	DlvrTo(String ID, String Sub, String Loc) {
		this.ID = ID;
		this.Sub = Sub;
		this.Loc = Loc;
	}
}

class Pty {
	String ID = "OCC";
	String R = "21";

	Pty() {
	}

	Pty(String ID, String R) {
		this.ID = ID;
		this.R = R;
	}
}

class Pty {
	String ID = "99999";
	String R = "4";

	Pty() {
	}

	Pty(String ID, String R) {
		this.ID = ID;
		this.R = R;
	}
}

class Pty {
	String ID = "C";
	String R = "38";
	Sub Sub_object = new Sub();

	Pty() {
	}

	Pty(String ID, String R, Sub Sub_) {
		this.ID = ID;
		this.R = R;
		this.Sub_object = Sub_;
	}
}

class Sub {
	String ID = "ZZZ";
	String Typ = "2";

	Sub() {
	}

	Sub(String ID, String Typ) {
		this.ID = ID;
		this.Typ = Typ;
	}
}

class Qty {
	String Typ = "SOD";
	String Long = "35";
	String Short = "0";

	Qty() {
	}

	Qty(String Typ, String Long, String Short) {
		this.Typ = Typ;
		this.Long = Long;
		this.Short = Short;
	}
}

class Qty {
	String Typ = "FIN";
	String Long = "20";
	String Short = "10";

	Qty() {
	}

	Qty(String Typ, String Long, String Short) {
		this.Typ = Typ;
		this.Long = Long;
		this.Short = Short;
	}
}

class Qty {
	String Typ = "IAS";
	String Long = "10";

	Qty() {
	}

	Qty(String Typ, String Long) {
		this.Typ = Typ;
		this.Long = Long;
	}
}

class Amt {
	String Typ = "FMTM";
	String Amt = "0.00";

	Amt() {
	}

	Amt(String Typ, String Amt) {
		this.Typ = Typ;
		this.Amt = Amt;
	}
}

class Instrmt {
	String Sym = "AOL";
	String ID = "KW";
	String IDSrc = "J";
	String CFI = "OCASPS";
	String MMY = "20031122";
	String Mat = "2003-11-22T00:00:00";
	String Strk = "47.50";
	String StrkCcy = "USD";
	String Mult = "100";

	Instrmt() {
	}

	Instrmt(String Sym, String ID, String IDSrc, String CFI, String MMY,
			String Mat, String Strk, String StrkCcy, String Mult) {
		this.Sym = Sym;
		this.ID = ID;
		this.IDSrc = IDSrc;
		this.CFI = CFI;
		this.MMY = MMY;
		this.Mat = Mat;
		this.Strk = Strk;
		this.StrkCcy = StrkCcy;
		this.Mult = Mult;
	}
}