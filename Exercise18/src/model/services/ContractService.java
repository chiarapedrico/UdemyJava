package model.services;

import java.time.LocalDate;

import model.entities.Contract;
import model.entities.Installment;

public class ContractService {

	private OnlinePaymentService onlinePayments;

	public ContractService(OnlinePaymentService onlinePayments) {
		this.onlinePayments = onlinePayments;
	}

	public void processContract(Contract contract, int months) {
		double basicQuota = contract.getTotalValue() / months;
		for(int i=1; i<= months; i++) {
			
			LocalDate dueDate = contract.getDate().plusMonths(i);
			double interest = onlinePayments.interest(basicQuota, i);
					double fee = onlinePayments.paymentFee(basicQuota + interest);
					double quota = basicQuota + interest + fee;
					
					contract .getInstallments().add(new Installment(dueDate,quota));
		}
	}
}
