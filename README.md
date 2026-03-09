# AIRTool

A desktop client application built with **Adobe AIR** and **Flex**, backed by a **Java/Seasar2** server. AIRTool provides staff-authenticated, module-based workflows with reporting, file transfer, and license management.

---

## Features

- **Staff login** — 教職員コード (staff code) and パスワード (password) authentication against the backend with program-permission checks (C12_PgmAuth)
- **Modular UI** — Loads SWF modules (e.g. HKC31400, HKC31410, MFUK*, MFUC*) from server or local storage
- **Deployment** — Browser-based install/update via `.air`; client downloads latest SWF modules and required files from the server on startup
- **Backend services**
  - **Reporting** — PrintReport, SavaToExcel, SavaToCsv, SavaToPDF
  - **File operations** — FileUpload, FileDownload, FolderUpload, FolderDownload
  - **License** — ManageLic
  - **Print** — PrintService, UserAgent

---

## Tech Stack

| Layer | Technology |
|-------|------------|
| **Client** | Adobe AIR 1.5, Flex 3.x (MXML/ActionScript 3), Flash Player 9.0.28 |
| **Client build** | Flex Builder (Eclipse), Flex SDK 3.1, locale `ja_JP` |
| **Backend** | Java, Seasar2 (S2Container, S2Flex2), AMF3 remoting |
| **Server** | Tomcat (e.g. 5.5), servlet-based (S2ContainerFilter, RemotingGateway) |
| **Database** | JDBC (default H2; Oracle, PostgreSQL, MySQL, SQL Server supported) |
| **Reporting** | JasperReports, iText, Apache POI (Excel), custom Excel/CSV/PDF export |
| **Other** | Merapi (native bridge), Spring, HK_Common.swc (shared Flex library) |

---

## Prerequisites

- **Eclipse** with JDT, Seasar (Dolteng, Diigu, DB Launcher), Tomcat nature
- **Flex Builder** (Apollo/AIR) or compatible Flex SDK 3.1
- **Apache Tomcat** (e.g. 5.5)
- **Java** (compatible with Seasar2 and Tomcat)
- **Adobe AIR runtime** (for running the desktop client)

---

## Project Structure

```
airtool/
├── .project, .classpath, .actionScriptProperties   # Eclipse / Flex Builder
├── src/
│   ├── java/                    # Backend Java (dbread, report, file, HKC, etc.)
│   ├── mxml/                    # Flex/AIR UI (AIRTool.mxml, modules, controls)
│   └── resources/               # Seasar2 dicon, JDBC, log4j, env
├── webapp/
│   ├── bin/                     # HTML launcher, AIR descriptor, connection.txt
│   ├── swf/                     # Built SWFs and config
│   ├── config/                  # Spring, Merapi config
│   ├── WEB-INF/
│   │   ├── web.xml              # Servlets, filters, gateway
│   │   ├── lib/                 # JARs (Seasar2, POI, JasperReports, etc.)
│   │   └── classes/             # dicon, log4j, env
│   └── browser/                 # history, AC_OETags
└── libs/                        # SWC (HK_Common.swc), JARs for Flex/Report
```

---

## Configuration

### Server connection

Edit `webapp/bin/connection.txt` (or `webapp/swf/connection.txt`) to point the client to your server:

```xml
<protocol>http</protocol>
<host>localhost</host>
<port>8080</port>
<context>AIRTool</context>
<html>AIRTool00000</html>
```

### Database

Configure JDBC in `webapp/WEB-INF/classes/jdbc.dicon`. Default is H2:

`jdbc:h2:tcp://localhost:9092/demo`

Optional environment overrides: `webapp/WEB-INF/classes/env.txt`.

### Required client files

`webapp/bin/requiredlist.txt` (and `webapp/swf/requiredlist.txt`) lists SWFs, JARs, and config files the client downloads. Key entries include `HK_Common.swf`, `AIRPrint.jar`, and libs for reporting (JasperReports, POI, Merapi, etc.).

---

## Installation & Running

### Backend (server)

1. Import the project into Eclipse with Seasar and Tomcat support.
2. Ensure classpath includes `src/java`, `src/resources`, and JARs under `webapp/WEB-INF/lib`.
3. Set database in `webapp/WEB-INF/classes/jdbc.dicon` (and optional `env.txt`).
4. Deploy the `webapp` to Tomcat (context path `AIRTool`). Remoting gateway: `/bin/gateway` or `/gateway`.

### Client (AIR)

1. Open the Flex/AIR project in Flex Builder (or Eclipse with Flex plugin). Main application: `AIRTool.mxml`.
2. Build the main app and modules; output goes to `webapp/swf` (e.g. `AIRTool.swf`, module SWFs).
3. AIR descriptor: `webapp/bin/AIRTool-app.xml` (or `webapp/swf/AIRTool-app.xml`) — AIR 1.5, content `AIRTool.swf`.

### Run from browser

1. Start Tomcat with the AIRTool webapp deployed.
2. Open: `http://localhost:8080/AIRTool/bin/AIRTool00000.html`  
   (adjust host/port/context if you changed `connection.txt`).
3. Install/launch the AIR app when prompted; it will use the server URL from the launcher/connection config.
4. Log in with staff code and password; modules load from the server.

### Run standalone

Install the built `.air` from `webapp/bin/` or `webapp/air/`. Ensure `connection.txt` (or server-supplied connection when launched from browser) points to the running server so the client can download SWFs and reach the gateway.

---

## License

See repository or project documentation for license terms.
