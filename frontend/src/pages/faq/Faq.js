import React, {useState} from 'react';
import FAQ from './FaqHelp';
import {useNavigate} from "react-router-dom";

function Faq() {

    const navigate = useNavigate("/")

    const [faqs, setfaqs] = useState([
        {
            question: 'What services and campaigns does Eski Camoluk Otomotiv offer?',
            answer: 'Thanks to the services and campaigns offered by Eski Camoluk Otomotiv, we ensure that you experience car rental at the highest level. We serve with the most common office network in Turkey. Eski Camoluk Otomotiv caters to both corporate and individual car rental needs. With our rich vehicle fleet of different group vehicles, we are ready to meet your car rental needs in the most accurate way.',
            open: false
        },
        {
            question: 'Can I find answers to specific questions about your services?',
            answer: 'Yes, our FAQ page covers a wide range of commonly asked questions. However, if you have a specific question that is not addressed here, please feel free to reach out to us directly.',
            open: false
        },
        {
            question: 'How do I contact customer support?',
            answer: 'You can contact our customer support team by calling our helpline at [phone number] or by sending an email to [email address]. We are available [days and hours of operation] to assist you with any queries or concerns.',
            open: false
        },
        {
            question: 'What payment methods do you accept?',
            answer: 'We accept various payment methods, including credit/debit cards (Visa, Mastercard, American Express), online payment platforms (PayPal, Stripe), and cash. Please note that payment methods may vary depending on the specific location and service.',
            open: false
        },
        {
            question: 'Are there any age restrictions for renting a vehicle?',
            answer: 'Yes, the minimum age requirement for renting a vehicle is typically 21 years old. However, some locations may have different age restrictions or additional requirements for young drivers. Please check the terms and conditions or contact our customer support for detailed information.',
            open: false
        },
        {
            question: 'What is your cancellation policy?',
            answer: 'Our cancellation policy may vary depending on the type of reservation and location. Generally, we offer a flexible cancellation policy that allows you to cancel or modify your reservation up to a certain period before the scheduled pickup time. However, specific terms and fees may apply. Please refer to the reservation confirmation email or contact our customer support for more details.',
            open: false
        },
        {
            question: 'Do you offer long-term car rental options?',
            answer: 'Yes, we offer long-term car rental options for individuals and businesses. Whether you need a vehicle for a month, several months, or even longer, we can provide customized solutions to meet your specific requirements. Please contact our long-term rental department for more information and pricing.',
            open: false
        }
    ]);

    const toggleFAQ = index => {
        setfaqs(faqs.map((faq, i) => {
            if (i === index) {
                faq.open = !faq.open
            } else {
                faq.open = false;
            }

            return faq;
        }))
    }
    const handleButtonClick = () => {
        navigate('/');
    };

    return (
        <div className="App">,
            <h1 style={{ textAlign: 'center' }}>Frequently Asked Questions</h1>
            <div className="faqs">
                {faqs.map((faq, i) => (
                    <FAQ faq={faq} index={i} toggleFAQ={toggleFAQ}/>
                ))}
            </div>

            <a
                className="whatsapp_floats"
                rel="noopener noreferrer"
                onClick={handleButtonClick}

            >

            </a>
        </div>
    );
}

export default Faq;