# GHA Demo - CI/CD Pipeline Sample Project

A comprehensive GitHub Actions CI/CD pipeline for a Java Spring Boot application following **trunk-based development** best practices.

![CI](https://github.com/YOUR_USERNAME/gha-demo/workflows/CI%20Pipeline/badge.svg)
![Release](https://github.com/YOUR_USERNAME/gha-demo/workflows/Release/badge.svg)

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────────────────────┐
│                           CI/CD Pipeline                                │
├─────────────────────────────────────────────────────────────────────────┤
│                                                                         │
│  Push to main ──► Build ──► Test ──► Quality ──► Docker ──► Deploy     │
│                    │         │         │          │          │          │
│                    ▼         ▼         ▼          ▼          ▼          │
│               [Cached]   [JaCoCo]  [Verify]   [Multi    [DEV→SIT→UAT]   │
│                          Coverage            Platform]  (with approval) │
│                                                                         │
└─────────────────────────────────────────────────────────────────────────┘
```

## 📁 Project Structure

```
gha-demo/
├── .github/
│   ├── workflows/
│   │   ├── ci.yml              # Main CI pipeline
│   │   ├── deploy.yml          # Environment deployments
│   │   ├── release.yml         # Semantic versioning
│   │   └── pr-checks.yml       # PR validation
│   └── dependabot.yml          # Dependency updates
│   └── CODEOWNERS              # Review assignments
├── src/
│   ├── main/java/...           # Application code
│   └── test/java/...           # Unit tests
├── Dockerfile                  # Multi-stage build
├── pom.xml                     # Maven config
└── README.md
```

## 🚀 Quick Start

### Prerequisites
- Java 21+
- Docker (optional, for container builds)

### Local Development

```bash
# Build
./mvnw package

# Run tests
./mvnw test

# Run application
./mvnw spring-boot:run

# Build Docker image
docker build -t gha-demo:local .

# Run container
docker run -p 8080:8080 gha-demo:local
```

### API Endpoints

| Endpoint | Description |
|----------|-------------|
| `GET /health` | Health check |
| `GET /api/greeting?name=X` | Greeting message |
| `GET /api/info` | Application info |
| `GET /actuator/health` | Actuator health |

## 🔄 CI/CD Workflows

### CI Pipeline (`ci.yml`)
Triggered on: `push` to `main`, `pull_request` to `main`

| Job | Purpose | Duration |
|-----|---------|----------|
| **Build** | Compile, package | ~1min |
| **Unit Test** | JUnit + JaCoCo coverage | ~1min |
| **Code Quality** | Verification checks | ~30s |
| **Docker** | Build & push to GHCR | ~2min |

**Optimizations:**
- ✅ Maven dependency caching
- ✅ Parallel job execution
- ✅ Docker layer caching
- ✅ Multi-platform builds (amd64/arm64)

### Deployment Pipeline (`deploy.yml`)
Triggered: After successful CI or manual dispatch

| Environment | Trigger | Protection |
|-------------|---------|------------|
| **Development** | Automatic | None |
| **SIT** | After dev success | None |
| **UAT** | After SIT success | **Required reviewers** |

### Release Pipeline (`release.yml`)
Triggered on: `push` to `main`

- Analyzes commits using conventional commit format
- Auto-determines version bump (patch/minor/major)
- Creates Git tag and GitHub Release
- Generates changelog

## 🛡️ Environment Setup

### 1. Create Environments in GitHub

1. Go to **Settings** → **Environments**
2. Create environments:
   - `development`
   - `sit`
   - `uat`

### 2. Configure UAT Protection Rules

For the `uat` environment:
1. Enable **Required reviewers**
2. Add team members who can approve deployments
3. (Optional) Enable **Prevent self-review**

### 3. Branch Protection (Recommended)

For `main` branch:
- ✅ Require pull request before merging
- ✅ Require status checks to pass
- ✅ Require branches to be up to date
- ✅ Do not allow bypassing the above settings

## 📋 Conventional Commits

Use conventional commit messages for automatic versioning:

| Prefix | Version Bump | Example |
|--------|--------------|---------|
| `fix:` | Patch (0.0.X) | `fix: resolve null pointer` |
| `feat:` | Minor (0.X.0) | `feat: add user endpoint` |
| `feat!:` or `BREAKING CHANGE:` | Major (X.0.0) | `feat!: redesign API` |

## 🐳 Docker

### Multi-stage Build
1. **Builder stage**: Compile with Maven (cached dependencies)
2. **Runtime stage**: Minimal JRE image

### Security Features
- Non-root user (`appuser`)
- Read-only filesystem compatible
- Health check configured
- JVM container optimizations

## 📦 Dependencies

Managed automatically via Dependabot:
- Maven dependencies (weekly)
- GitHub Actions (weekly)
- Docker base images (weekly)

## 📄 License

MIT License - see [LICENSE](LICENSE)
