import React from 'react'
import "./Faq.css";
function FAQHelp ({faq, index, toggleFAQ}) {
    return (
        <div
            className={"faq " + (faq.open ? 'open' : '')}
            key={index}
            onClick={() => toggleFAQ(index)}
        >
            <div className="faq-question">
                {faq.question}
            </div>
            <div className="faq-answer">
                {faq.answer}
            </div>
        </div>
    )
}

export default FAQHelp