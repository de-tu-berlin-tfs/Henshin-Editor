class ClOrdID {
	ClOrdID() {
	}
}

class MinQty {
	MinQty() {
	}
}

class Symbol {
	Symbol() {
	}
}

class IDSource {
	IDSource() {
	}
}

class SecurityID {
	SecurityID() {
	}
}

class SecurityID {
	SecurityID() {
	}
}

class TransactTime {
	TransactTime() {
	}
}

class OrderQty {
	OrderQty() {
	}
}

class Price {
	Price() {
	}
}

class Order {
	Order() {
	}
}

class ApplicationMessage {
	ApplicationMessage() {
	}
}

class FIXMLMessage {
	FIXMLMessage() {
	}
}

class FIXML {
	FIXML() {
	}
}

class FIXMLMessage {
	ApplicationMessage ApplicationMessage_object = new ApplicationMessage();
	Instrument Instrument_object = new Instrument();
	IDSource IDSource_object = new IDSource();

	FIXMLMessage() {
	}

	FIXMLMessage(ApplicationMessage ApplicationMessage_,
			Instrument Instrument_, IDSource IDSource_) {
		this.ApplicationMessage_object = ApplicationMessage_;
		this.Instrument_object = Instrument_;
		this.IDSource_object = IDSource_;
	}
}

class ApplicationMessage {
	Order Order_object = new Order();
	HandInst HandInst_object = new HandInst();
	MinQty MinQty_object = new MinQty();

	ApplicationMessage() {
	}

	ApplicationMessage(Order Order_, HandInst HandInst_, MinQty MinQty_) {
		this.Order_object = Order_;
		this.HandInst_object = HandInst_;
		this.MinQty_object = MinQty_;
	}
}

class Order {
	ClOrdID ClOrdID_object = new ClOrdID();

	Order() {
	}

	Order(ClOrdID ClOrdID_) {
		this.ClOrdID_object = ClOrdID_;
	}
}

class HandInst {
	String Value = "2";

	HandInst() {
	}

	HandInst(String Value) {
		this.Value = Value;
	}
}

class Instrument {
	Symbol Symbol_object = new Symbol();

	Instrument() {
	}

	Instrument(Symbol Symbol_) {
		this.Symbol_object = Symbol_;
	}
}

class Instrument {
	Side Side_object = new Side();
	TransactTime TransactTime_object = new TransactTime();

	Instrument() {
	}

	Instrument(Side Side_, TransactTime TransactTime_) {
		this.Side_object = Side_;
		this.TransactTime_object = TransactTime_;
	}
}

class Side {
	String Value = "1";

	Side() {
	}

	Side(String Value) {
		this.Value = Value;
	}
}

class OrderQuantity {
	OrderQty OrderQty_object = new OrderQty();

	OrderQuantity() {
	}

	OrderQuantity(OrderQty OrderQty_) {
		this.OrderQty_object = OrderQty_;
	}
}

class OrderQuantity {
	OrderType OrderType_object = new OrderType();

	OrderQuantity() {
	}

	OrderQuantity(OrderType OrderType_) {
		this.OrderType_object = OrderType_;
	}
}

class OrderType {
	LimitOrder LimitOrder_object = new LimitOrder();

	OrderType() {
	}

	OrderType(LimitOrder LimitOrder_) {
		this.LimitOrder_object = LimitOrder_;
	}
}

class LimitOrder {
	String Value = "2";
	Price Price_object = new Price();

	LimitOrder() {
	}

	LimitOrder(String Value, Price Price_) {
		this.Value = Value;
		this.Price_object = Price_;
	}
}

class Currency {
	String Value = "USD";

	Currency() {
	}

	Currency(String Value) {
		this.Value = Value;
	}
}

class Rule80A {
	String Value = "A";

	Rule80A() {
	}

	Rule80A(String Value) {
		this.Value = Value;
	}
}