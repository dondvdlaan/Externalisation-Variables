// ---- Dependencies ----
const express       = require("express");
const server        = express();

const cors          = require("cors");
const bodyParser    = require("body-parser");

// Loads .env file chosen at "npm run dev/stage/prod" start-up command
require('dotenv').config({ path: `.env.${process.env.NODE_ENV}` })

// ---- Modules ----
const logger    = require("./logger/logger");
const auditPool = require("./utils/auditDB.js");
const feLogger  = require("./controllers/feLogger.js");


// ---- Constants ----
const serverPort = process.env.SERVER_PORT;

// ---- Middleware ----
// req.body is populated 
server.use(bodyParser.json());
// CORS
server.use(cors());


/**
 * Initialisation NodeBE
*/
const init = () => {
    
    // Initialise Controller FE logger
    feLogger(server, auditPool);
    
    // ---- Starting up Node backend ----
    server.listen(serverPort, () => {
        
        console.log(`Server listening at ${serverPort}\n`);
        logger.info('Server started');
    });
}

init();