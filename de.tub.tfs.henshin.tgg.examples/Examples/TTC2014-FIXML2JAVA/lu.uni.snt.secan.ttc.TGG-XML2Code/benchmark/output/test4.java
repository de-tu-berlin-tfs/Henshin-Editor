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
    FIXMLMessage FIXMLMessage_object = new FIXMLMessage();

    FIXML() {
    }

    FIXML(FIXMLMessage FIXMLMessage_) {
	this.FIXMLMessage_object = FIXMLMessage_;
    }
}

class FIXMLMessage {
    Header Header_object = new Header();
    ApplicationMessage ApplicationMessage_object = new ApplicationMessage();

    FIXMLMessage() {
    }

    FIXMLMessage(Header Header_, ApplicationMessage ApplicationMessage_) {
	this.Header_object = Header_;
	this.ApplicationMessage_object = ApplicationMessage_;
    }
}

class Header {
    Sender Sender_object = new Sender();
    Target Target_object = new Target();
    SendingTime SendingTime_object = new SendingTime();

    Header() {
    }

    Header(Sender Sender_, Target Target_, SendingTime SendingTime_) {
	this.Sender_object = Sender_;
	this.Target_object = Target_;
	this.SendingTime_object = SendingTime_;
    }
}

class Sender {
    CompID CompID_object = new CompID();
    SubID SubID_object = new SubID();

    Sender() {
    }

    Sender(CompID CompID_, SubID SubID_) {
	this.CompID_object = CompID_;
	this.SubID_object = SubID_;
    }
}

class CompID {
    String __VALUE = "MEEF";

    CompID() {
    }

    CompID(String __VALUE) {
	this.__VALUE = __VALUE;
    }
}

class SubID {
    String __VALUE = "IAN";

    SubID() {
    }

    SubID(String __VALUE) {
	this.__VALUE = __VALUE;
    }
}

class Target {
    CompID CompID_object = new CompID();
    SubID SubID_object = new SubID();

    Target() {
    }

    Target(CompID CompID_, SubID SubID_) {
	this.CompID_object = CompID_;
	this.SubID_object = SubID_;
    }
}

class SendingTime {
    String __VALUE = "20010226-15:00:00";

    SendingTime() {
    }

    SendingTime(String __VALUE) {
	this.__VALUE = __VALUE;
    }
}

class ApplicationMessage {
    Order Order_object = new Order();

    ApplicationMessage() {
    }

    ApplicationMessage(Order Order_) {
	this.Order_object = Order_;
    }
}

class Order {
    ClOrdID ClOrdID_object = new ClOrdID();
    HandInst HandInst_object = new HandInst();
    Instrument Instrument_object = new Instrument();
    Side Side_object = new Side();
    TransactTime TransactTime_object = new TransactTime();
    OrderQuantity OrderQuantity_object = new OrderQuantity();
    OrderType OrderType_object = new OrderType();
    Currency Currency_object = new Currency();
    OrderDuration OrderDuration_object = new OrderDuration();
    Rule80A Rule80A_object = new Rule80A();

    Order() {
    }

    Order(ClOrdID ClOrdID_, HandInst HandInst_, Instrument Instrument_,
	    Side Side_, TransactTime TransactTime_,
	    OrderQuantity OrderQuantity_, OrderType OrderType_,
	    Currency Currency_, OrderDuration OrderDuration_, Rule80A Rule80A_) {
	this.ClOrdID_object = ClOrdID_;
	this.HandInst_object = HandInst_;
	this.Instrument_object = Instrument_;
	this.Side_object = Side_;
	this.TransactTime_object = TransactTime_;
	this.OrderQuantity_object = OrderQuantity_;
	this.OrderType_object = OrderType_;
	this.Currency_object = Currency_;
	this.OrderDuration_object = OrderDuration_;
	this.Rule80A_object = Rule80A_;
    }
}

class ClOrdID {
    String __VALUE = "FOO 1234";

    ClOrdID() {
    }

    ClOrdID(String __VALUE) {
	this.__VALUE = __VALUE;
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
    SecurityType SecurityType_object = new SecurityType();

    Instrument() {
    }

    Instrument(Symbol Symbol_, SecurityType SecurityType_) {
	this.Symbol_object = Symbol_;
	this.SecurityType_object = SecurityType_;
    }
}

class Symbol {
    String __VALUE = "CSCO";

    Symbol() {
    }

    Symbol(String __VALUE) {
	this.__VALUE = __VALUE;
    }
}

class SecurityType {
    Equity Equity_object = new Equity();

    SecurityType() {
    }

    SecurityType(Equity Equity_) {
	this.Equity_object = Equity_;
    }
}

class Equity {
    String Value = "CS";

    Equity() {
    }

    Equity(String Value) {
	this.Value = Value;
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

class TransactTime {
    String __VALUE = "20010226-15:00:00";

    TransactTime() {
    }

    TransactTime(String __VALUE) {
	this.__VALUE = __VALUE;
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

class OrderQty {
    String __VALUE = "5000";

    OrderQty() {
    }

    OrderQty(String __VALUE) {
	this.__VALUE = __VALUE;
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
    Price Price_object = new Price();

    LimitOrder() {
    }

    LimitOrder(Price Price_) {
	this.Price_object = Price_;
    }
}

class Price {
    String __VALUE = "32.25";

    Price() {
    }

    Price(String __VALUE) {
	this.__VALUE = __VALUE;
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

class OrderDuration {
    TimeInForce TimeInForce_object = new TimeInForce();

    OrderDuration() {
    }

    OrderDuration(TimeInForce TimeInForce_) {
	this.TimeInForce_object = TimeInForce_;
    }
}

class TimeInForce {
    String Value = "0";

    TimeInForce() {
    }

    TimeInForce(String Value) {
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