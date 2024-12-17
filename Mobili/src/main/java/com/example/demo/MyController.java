package com.example.demo;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;

import jakarta.mail.MessagingException;


@Controller
public class MyController {
	
	@Autowired
	JdbcTemp p1;
	
	@Autowired
	EmailService emailService;
	
	@Value("${stripe.currency}")
	private String currency;
	
	@Value("${stripe.secretKey}")
    private String stripeSecretKey;
	
	ArrayList<MobiliA> carrello = new ArrayList<>();
	
	@GetMapping("/formUtente")
	public String getFormUt(Model model) {
		ArrayList<Mobili> listaM = p1.getLista();
		
		model.addAttribute("listaM", listaM);
		return "formUtente";
	}
	

	@GetMapping("private/form")
	public String getForm(Model model) {
		ArrayList<Mobili> listaM = p1.getLista();
		
		model.addAttribute("listaM", listaM);
		return "form";
	}
	
	@CrossOrigin(origins = "*") 
	@ResponseBody
	@GetMapping("/lista")
	public ArrayList<MobiliA> getLista(){
		
		ArrayList<MobiliA> lista = p1.getListaA();
		
		return lista;
		
	}

	@ResponseBody
	@PostMapping("/submit")
	public String gestoreForm(@RequestParam("nome") String nome, @RequestParam("marca") String marca, 
			@RequestParam("prezzo") double prezzo, @RequestParam("url") String url, Model model) {
		boolean verifica=false;
		
		
		if(p1.insertMobile(nome, marca, prezzo, url)>0) {
			verifica=true;
			
			model.addAttribute("verifica", verifica);
			System.out.println(verifica);
		}
		System.out.println("  ******** "+verifica);
		ArrayList<Mobili> listaM = p1.getLista();
		model.addAttribute("listaM", listaM);
		
		return " mobile aggiunto con successo";	
	}
	
	@ResponseBody
	@PostMapping("/updateP")
	public String updatePrezzo(@RequestParam("nome") String nome, @RequestParam("prezzo") double prezzo) {
		
		p1.updatePrezzo(nome, prezzo);
		
		return " prezzo di " + nome + " aggiornato con successo";	
	}
	
	@ResponseBody
	@PostMapping("/updateQ")
	public String updateQuantita(@RequestParam("nome") String nome, @RequestParam("quantita") int quantita) {
		
		p1.updateQuantita(nome, quantita);
		
		return " quantita di " + nome + " aggiornato con successo";	
	}
	
	@ResponseBody
	@PostMapping("/delete")
	public String delete(@RequestParam("nome") String nome) {
		
		p1.delete(nome);
		
		return nome + " cancellato con successo";	
	}
	
	/*Lato utonto*/
	
	
	@GetMapping("/")
	public String getStore(Model model) {
		ArrayList<Mobili> listaM = p1.getLista();
		
		model.addAttribute("listaM", listaM);
		
		return "store";
	}
	
	
	@PostMapping("/add")
	public String add(Model model, @RequestParam("selected")  int selectedG,@RequestParam("nome") String nome  ) {
		
		System.out.println(selectedG);
		System.out.println(nome);
		boolean trovato = false;
		ArrayList<Mobili> listaM = p1.getLista();
		
		int indice = -1;
		double totale=0;
		
    	  
    	  if (carrello.size() == 0) {
    		  for (int i = 0; i < listaM.size(); i++ ) {
    			  
    			  if (listaM.get(i).getNome().equals(nome)) {
				
    				  
    				  MobiliA mA1 = new MobiliA();
    				  
    				  mA1.setNome(listaM.get(i).getNome());
    				  mA1.setMarca(listaM.get(i).getMarca());
    				  mA1.setPrezzo(listaM.get(i).getPrezzo());
    				  mA1.setUrl(listaM.get(i).getUrl());
    				  mA1.setQuantita(selectedG);
    	
    				  System.out.println("sono il primo");
    				  carrello.add(mA1);
    			  }
			  }
		  }
    	  else {
    		 System.out.println("sono qui");
    		 
			 for (int i = 0; i < listaM.size(); i++ ) {
			
				 if (listaM.get(i).getNome().equals(nome)) {
			
				 	for (int j = 0; j < carrello.size(); j++) {
				 		System.out.println(carrello.get(j).getNome());
				
				 		if (carrello.get(j).getNome().equals(nome) == true) {
				 			
				 			System.out.println(trovato);
				 			trovato = true;
				 		}
				 		else {
				 			indice = i;
				 		}
				 	}
		
				 }
			 }
    	 
		 
		 
			 if (trovato == false) {
		 
				  MobiliA mA1 = new MobiliA();
				  
				  mA1.setNome(listaM.get(indice).getNome());
				  mA1.setMarca(listaM.get(indice).getMarca());
				  mA1.setPrezzo(listaM.get(indice).getPrezzo());
				  mA1.setUrl(listaM.get(indice).getUrl());
				  mA1.setQuantita(mA1.getQuantita()+selectedG);
				 				 
				  System.out.println("inserito perchè diverso");
				  carrello.add(mA1);
			 }
			 else {
	
				 for (int j = 0; j < carrello.size(); j++) {
		
		
					 System.out.println(carrello.get(j).getNome());
		
					 if (carrello.get(j).getNome().equals(nome) == true) {
	
						 
						 carrello.get(j).setQuantita(selectedG);
	
					 }
				 }
			 }
		 }
    	  
    	  for (MobiliA prodotto : carrello) {
  	        totale += prodotto.getQuantita() * prodotto.getPrezzo();  
  	    }
		
    	model.addAttribute("totP", totale);
		model.addAttribute("carrello", carrello);
		
		return getStore(model);
	}
	
	@PostMapping("/buy")
	public String recap(@RequestParam("mail") String mail, @RequestParam("token") String token, Model model) throws MessagingException {
			
		int somma=0;
		ArrayList<Mobili> listaM= p1.getLista();
		ArrayList<MobiliA> listaMA = new ArrayList<>();
		ArrayList<String> listaU = new ArrayList<>();
		
		String soggetto = "Hai acquistato: ";
		
		for(int i=0; i<carrello.size(); i++) {
			if(carrello.get(i).getQuantita()>0) {
				somma += carrello.get(i).getQuantita()*carrello.get(i).getPrezzo();
				
				p1.change(carrello.get(i).getNome(), carrello.get(i).getQuantita());
				listaU.add(carrello.get(i).getUrl());
				soggetto += carrello.get(i).getNome() + ", ";
				
			}
		}
		
		soggetto += " La somma totale da pagare è: " + somma + " euro";
		emailService.sendEmailWithImage(mail, "mail da talentform-mobili", soggetto, listaU);
		
		//System.out.println("La somma totale da pagare è: " + somma + " euro");
		model.addAttribute("carrello", carrello);
		model.addAttribute("somma", somma);
		
		  try {
	            // Imposta la chiave segreta di Stripe
	            Stripe.apiKey = stripeSecretKey;

	            // Crea una richiesta di pagamento
	            ChargeCreateParams params = ChargeCreateParams.builder()
	                    .setAmount((long)  somma * 100) // Importo in centesimi
	                    .setCurrency("eur")
	                    .setSource(token) // Usa il token di test fornito
	                    .build();
	            
	            
	            Charge charge = Charge.create(params);
	            
	            System.out.println("Pagamento completato: " + charge.toJson());
	            
	            System.out.println("Visualizza ricevuta: " +  charge.getReceiptUrl());
	            
	         //risult = "Pagamento andato a buon fine";
	         
	         model.addAttribute("urlRicevuta",charge.getReceiptUrl());
         //  ok = true;
	            
	            
	        } catch (StripeException e) {
	        	
	        	//risult = "Pagamento non riuscito, riprova";
	        	//ok = false;
	        	 
	        }
		
		return "recap";
	}
}
